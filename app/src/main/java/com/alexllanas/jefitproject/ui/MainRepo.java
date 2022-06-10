package com.alexllanas.jefitproject.ui;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alexllanas.jefitproject.data.db.BusinessDao;
import com.alexllanas.jefitproject.data.db.CityDao;
import com.alexllanas.jefitproject.data.network.utils.ApiResponse;
import com.alexllanas.jefitproject.data.network.utils.NetworkBoundResource;
import com.alexllanas.jefitproject.data.network.utils.Resource;
import com.alexllanas.jefitproject.data.network.ReviewResponse;
import com.alexllanas.jefitproject.data.network.SearchResponse;
import com.alexllanas.jefitproject.data.network.YelpApiService;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.features.detail.Review;
import com.alexllanas.jefitproject.util.AppExecutors;
import com.alexllanas.jefitproject.util.Constants;
import com.alexllanas.jefitproject.util.NetworkHelper;
import com.alexllanas.jefitproject.util.StaticData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledExecutorService;

import javax.inject.Inject;

public class MainRepo {


    private final YelpApiService apiService;
    private final CityDao cityDao;
    private final BusinessDao businessDao;
    private final NetworkHelper networkHelper;

    @Inject
    public MainRepo(YelpApiService apiService, CityDao cityDao, BusinessDao businessDao, NetworkHelper networkHelper) {
        this.apiService = apiService;
        this.cityDao = cityDao;
        this.businessDao = businessDao;
        this.networkHelper = networkHelper;
    }

    public void populateDatabase() {
        AppExecutors.getInstance().diskIO().execute(() ->
                cityDao.insertCities(StaticData.CITIES)
        );
    }

    public LiveData<ArrayList<City>> getCities() {
        ArrayList<City> cities = new ArrayList<>();
        AppExecutors.getInstance().diskIO().execute(() -> {
            CountDownLatch latch = new CountDownLatch(1);
            List<City> result = cityDao.getCities();
            latch.countDown();
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result == null || result.isEmpty()) {
                cityDao.insertCities(StaticData.CITIES);
                result = cityDao.getCities();
            }
            cities.addAll(result);
        });
        return new MutableLiveData<>(cities);
    }

    public void likeBusiness(Business business) {
        business.setLiked(!business.isLiked);
        AppExecutors.getInstance().diskIO().execute(() -> businessDao.updateBusiness(business));
    }


    public LiveData<Resource<ArrayList<Business>>> getBusinesses(String cityName) {
        return new NetworkBoundResource<ArrayList<Business>, SearchResponse>(AppExecutors.getInstance(), networkHelper) {

            /**
             *   Update city with list of business IDs from search results and
             *   insert fetched businesses into db
             * @param item Network response
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveCallResult(@NonNull SearchResponse item) {
                ArrayList<String> businessIds = new ArrayList<>();
                item.businesses.forEach(business -> businessIds.add(business.businessId));
                City updateCity = new City(cityName, businessIds);
                cityDao.updateCity(updateCity);

                businessDao.insertBusiness(item.businesses);
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<Business> data) {
                return (data == null || data.isEmpty()) && networkHelper.isNetworkConnected();
            }

            /**
             * Get list of business IDs from db and convert to list of Businesses
             * @return LivData of business list
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @NonNull
            @Override
            protected LiveData<ArrayList<Business>> loadFromDb() {
                CountDownLatch latch = new CountDownLatch(2);
                ArrayList<Business> businesses = new ArrayList<>();
                ArrayList<String> businessIds = new ArrayList<>();
                AppExecutors.getInstance().diskIO().execute(() -> {
                    City city = cityDao.getCity(cityName);
                    if (city != null) {
                        businessIds.addAll(city.businessIds);
                    }
                    latch.countDown();
                });

                AppExecutors.getInstance().diskIO().execute(() -> {
                    businessIds.forEach(id -> {
                        businesses.add(businessDao.getBusiness(id));
                    });
                    latch.countDown();
                });

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new MutableLiveData<>(businesses);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SearchResponse>> createCall() {
                return apiService.getBusinesses(Constants.API_KEY_TOKEN, cityName);
            }
        }.

                getAsLiveData();
    }

    public LiveData<Business> getBusiness(String businessId) {
        Business business = new Business();
        CountDownLatch latch = new CountDownLatch(1);
        AppExecutors.getInstance().diskIO().execute(() -> {
            Business temp = businessDao.getBusiness(businessId);
            business.from(temp);
            latch.countDown();
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new MutableLiveData<>(business);
    }

    public LiveData<Resource<ArrayList<Review>>> getReviews(String businessId) {
        return new NetworkBoundResource<ArrayList<Review>, ReviewResponse>(AppExecutors.getInstance(), networkHelper) {

            @Override
            protected void saveCallResult(@NonNull ReviewResponse item) {
                Business business = businessDao.getBusiness(businessId);
                business.setReviews(item.reviews);
                businessDao.updateBusiness(business);
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<Review> data) {
                return (data == null || data.isEmpty()) && networkHelper.isNetworkConnected();
            }

            @NonNull
            @Override
            protected LiveData<ArrayList<Review>> loadFromDb() {
                CountDownLatch latch = new CountDownLatch(1);
                ArrayList<Review> reviews = new ArrayList<>();
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Business temp = businessDao.getBusiness(businessId);
                    ArrayList<Review> results = temp.reviews;
                    if (results != null) {
                        reviews.addAll(results);
                    }
                    latch.countDown();
                });

                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new MutableLiveData<>(reviews);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ReviewResponse>> createCall() {
                return apiService.getReviews(Constants.API_KEY_TOKEN, businessId);
            }
        }.getAsLiveData();
    }
}
