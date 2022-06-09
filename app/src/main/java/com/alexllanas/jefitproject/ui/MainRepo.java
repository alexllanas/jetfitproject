package com.alexllanas.jefitproject.ui;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.alexllanas.jefitproject.R;
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
import com.alexllanas.jefitproject.util.StaticData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class MainRepo {

    private final YelpApiService apiService;
    private final CityDao cityDao;
    private final BusinessDao businessDao;

    @Inject
    public MainRepo(YelpApiService apiService, CityDao cityDao, BusinessDao businessDao) {
        this.apiService = apiService;
        this.cityDao = cityDao;
        this.businessDao = businessDao;
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

    public LiveData<Resource<ArrayList<Business>>> getBusinesses(String cityName) {
        return new NetworkBoundResource<ArrayList<Business>, SearchResponse>(AppExecutors.getInstance()) {

            /**
             *   Update city with list of business IDs from search results and
             *   insert fetched businesses into db
             * @param item Network response
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            protected void saveCallResult(@NonNull SearchResponse item) {
                List<String> businessIds = item.businesses.stream().map(business ->
                        business.businessId
                ).collect(Collectors.toList());
                City updateCity = new City(cityName, (ArrayList<String>) businessIds);
                cityDao.updateCity(updateCity);

                businessDao.insertBusiness(item.businesses);
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<Business> data) {
                ArrayList<String> ids = new ArrayList<>();
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        City temp = cityDao.getCity(cityName);
                        ids.addAll(temp.businessIds);
                    }
                });
                return ids.isEmpty();
            }

            /**
             * Get list of business IDs from db and convert to list of Businesses
             * @return LivData of business list
             */
            @RequiresApi(api = Build.VERSION_CODES.N)
            @NonNull
            @Override
            protected LiveData<ArrayList<Business>> loadFromDb() {
                ArrayList<Business> businesses = new ArrayList<>();
                AppExecutors.getInstance().diskIO().execute(() -> {
                    City city = cityDao.getCity(cityName);
                    if (city != null) {
                        if (city.businessIds != null) {
                            city.businessIds.forEach(id -> {
                                businesses.add(businessDao.getBusiness(id));
                            });

                        }
                    }
                });
                MutableLiveData<ArrayList<Business>> businessLiveData = new MutableLiveData<>();
                businessLiveData.setValue(businesses);
                return businessLiveData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SearchResponse>> createCall() {
                return apiService.getBusinesses(Constants.API_KEY_TOKEN, cityName);
            }
        }.getAsLiveData();
    }
}
