package com.alexllanas.jefitproject.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;


import com.alexllanas.jefitproject.data.network.Resource;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.CityRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    public MediatorLiveData<MainState> mainState = new MediatorLiveData<>();
    private CityRepo cityRepo;

    @Inject
    public MainViewModel(CityRepo repo) {
        cityRepo = repo;
    }

    public void getBusinesses(String city) {

        LiveData<Resource<ArrayList<Business>>> repoSource = cityRepo.getBusinesses(city);
        mainState.addSource(repoSource, arrayListResource -> {
            if (arrayListResource.status == Resource.Status.SUCCESS) {
                MainState currentState = getCurrentOrNewState();
                MainState newState = currentState.copy();
                newState.setBusinessList(arrayListResource.data);
                newState.setLoading(false);
                mainState.setValue(newState);
            }
            mainState.removeSource(repoSource);
        });
    }

    private MainState getCurrentOrNewState() {
        MainState currentState = mainState.getValue();
        if (currentState != null) return currentState;
        return new MainState();
    }
}
