package com.alexllanas.jefitproject.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.alexllanas.jefitproject.data.network.Resource;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.util.NetworkHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MediatorLiveData<ArrayList<City>> _cities = new MediatorLiveData<>();
    private final MediatorLiveData<ArrayList<Business>> _businesses = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    private final MainRepo repository;
    private final NetworkHelper networkHelper;
    LiveData<Resource<ArrayList<Business>>> businessSource;

    @Inject
    public MainViewModel(MainRepo repo, NetworkHelper networkHelper) {
        this.repository = repo;
        this.networkHelper = networkHelper;
    }

    public LiveData<ArrayList<Business>> businessList() {
        return _businesses;
    }

    public LiveData<ArrayList<City>> cityList() {
        return _cities;
    }

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public void getBusinesses(String city) {
        if (businessSource != null) {
            _businesses.removeSource(businessSource);
        }
        businessSource = repository.getBusinesses(city);
        _businesses.addSource(businessSource, resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                _businesses.setValue(resource.data);
                _isLoading.setValue(false);
            } else if (resource.status == Resource.Status.LOADING) {
                _isLoading.setValue(true);
            }
        });
    }

    public void getCities() {
        _isLoading.setValue(true);
        LiveData<ArrayList<City>> repoSource = repository.getCities();
        _cities.addSource(repoSource, cityList -> {
            _cities.setValue(cityList);
            _isLoading.setValue(false);
            _cities.removeSource(repoSource);
        });
    }

    public void populateDatabase() {
        repository.populateDatabase();
    }

    public void likeBusiness(Business business) {
        repository.likeBusiness(business);
    }
}
