package com.alexllanas.jefitproject.ui;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alexllanas.jefitproject.data.db.BusinessDao;
import com.alexllanas.jefitproject.data.db.CityDao;
import com.alexllanas.jefitproject.data.network.ApiResponse;
import com.alexllanas.jefitproject.data.network.NetworkBoundResource;
import com.alexllanas.jefitproject.data.network.Resource;
import com.alexllanas.jefitproject.data.network.SearchResponse;
import com.alexllanas.jefitproject.data.network.YelpApiService;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.util.AppExecutors;
import com.alexllanas.jefitproject.util.Constants;
import com.alexllanas.jefitproject.util.NetworkHelper;
import com.alexllanas.jefitproject.util.StaticData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
            List<City> result = cityDao.getCities();
            if (result != null) {
                cities.addAll(result);
            }
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
                CountDownLatch latch = new CountDownLatch(1);
                ArrayList<Business> businesses = new ArrayList<>();
                ArrayList<String> businessIds = new ArrayList<>();
                AppExecutors.getInstance().diskIO().execute(() -> {
                    City city = cityDao.getCity(cityName);
                    if (city != null) {
                        businessIds.addAll(city.businessIds);
                    }
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
}
