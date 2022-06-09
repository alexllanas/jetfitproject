package com.alexllanas.jefitproject.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;


import com.alexllanas.jefitproject.data.network.Resource;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    public MediatorLiveData<MainState> mainState = new MediatorLiveData<>();
    private final MainRepo repository;

    @Inject
    public MainViewModel(MainRepo repo) {
        this.repository = repo;
    }

    public void getBusinesses(String city) {
        LiveData<Resource<ArrayList<Business>>> repoSource = repository.getBusinesses(city);
        mainState.addSource(repoSource, resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                MainState currentState = getCurrentOrNewState();
                MainState newState = currentState.copy();
                newState.setBusinessList(resource.data);
                newState.setLoading(false);
                mainState.setValue(newState);
            }
            mainState.removeSource(repoSource);
        });
    }

    public void getCities() {
        LiveData<ArrayList<City>> repoSource = repository.getCities();
        mainState.addSource(repoSource, cityList -> {
                MainState currentState = getCurrentOrNewState();
                MainState newState = currentState.copy();
                newState.setCityList(cityList);
                newState.setLoading(false);
                mainState.setValue(newState);
        });
    }


    private MainState getCurrentOrNewState() {
        MainState currentState = mainState.getValue();
        if (currentState != null) return currentState;
        return new MainState();
    }

    public void populateDatabase() {
        repository.populateDatabase();
    }
}
