package com.alexllanas.jefitproject.features.city;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Transaction;

import com.alexllanas.jefitproject.data.db.CityDao;
import com.alexllanas.jefitproject.data.network.ApiResponse;
import com.alexllanas.jefitproject.data.network.NetworkBoundResource;
import com.alexllanas.jefitproject.data.network.Resource;
import com.alexllanas.jefitproject.data.network.SearchResponse;
import com.alexllanas.jefitproject.data.network.YelpApiService;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.util.AppExecutors;
import com.alexllanas.jefitproject.util.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

public class CityRepo {

    private final YelpApiService apiService;
    private final CityDao cityDao;

    @Inject
    public CityRepo(YelpApiService apiService, CityDao cityDao) {
        this.apiService = apiService;
        this.cityDao = cityDao;
    }

    public LiveData<Resource<ArrayList<Business>>> getBusinesses(String cityName) {
        return new NetworkBoundResource<ArrayList<Business>, SearchResponse>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull SearchResponse item) {
                CityWithBusinesses city = new CityWithBusinesses(new City(cityName), item.businesses);
                upsert(city);
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<Business> data) {
                LiveData<CityWithBusinesses> city = cityDao.getCities(cityName);
                if (city.getValue() != null) {
                    return false;
                } else {
                    return true;
                }
            }

            @NonNull
            @Override
            protected LiveData<ArrayList<Business>> loadFromDb() {
                return Transformations.map(cityDao.getCities(cityName), city ->
                        city.businesses
                );
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SearchResponse>> createCall() {
                return apiService.getBusinesses(Constants.API_KEY_TOKEN, cityName);
            }
        }.getAsLiveData();
    }

    @Transaction
    void upsert(CityWithBusinesses city) {
        long id = cityDao.insertCity(city);
        if (id == -1) {
            cityDao.updateCity(city);
        }
    }
}
