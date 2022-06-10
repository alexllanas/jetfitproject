package com.alexllanas.jefitproject.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.alexllanas.jefitproject.data.network.utils.Resource;
import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.features.detail.Review;
import com.alexllanas.jefitproject.util.AppExecutors;
import com.alexllanas.jefitproject.util.NetworkHelper;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final MediatorLiveData<ArrayList<City>> _cities = new MediatorLiveData<>();
    private final MediatorLiveData<ArrayList<Business>> _businesses = new MediatorLiveData<>();
    private final MediatorLiveData<ArrayList<Review>> _reviews = new MediatorLiveData<>();
    private final MediatorLiveData<Business> _business = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    private final MainRepo repository;
    private final NetworkHelper networkHelper;
    LiveData<Resource<ArrayList<Business>>> businessSource;
    LiveData<Resource<ArrayList<Review>>> reviewSource;

    @Inject
    public MainViewModel(MainRepo repo, NetworkHelper networkHelper) {
        this.repository = repo;
        this.networkHelper = networkHelper;
    }

    public LiveData<ArrayList<Business>> businessList() {
        return _businesses;
    }

    public LiveData<ArrayList<Review>> reviewsList() {
        return _reviews;
    }

    public LiveData<Business> business() {
        return _business;
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

    public void getBusiness(String businessId) {
        _isLoading.setValue(true);
        LiveData<Business> repoSource = repository.getBusiness(businessId);
        _business.addSource(repoSource, business -> {
            _business.setValue(business);
            _isLoading.setValue(false);
            _business.removeSource(repoSource);
        });
    }

    public void getReviews(String businessId) {
        if (reviewSource != null) {
            _business.removeSource(reviewSource);
        }

        getBusiness(businessId);
        reviewSource = repository.getReviews(businessId);
        _business.addSource(reviewSource, resource -> {
            if (resource.status == Resource.Status.SUCCESS) {
                Business updateBusiness = _business.getValue();
                if (updateBusiness != null) {
                    updateBusiness.reviews = resource.data;
                    _business.setValue(updateBusiness);
                }
//                _isLoading.setValue(false);
            } else if (resource.status == Resource.Status.LOADING) {
//                _isLoading.setValue(true);
            }
        });
    }

    public void getBusinessDetails(String businessId) {

//        AppExecutors.getInstance().mainThread().execute(new Runnable() {
//            @Override
//            public void run() {
        getReviews(businessId);
//            }
//        });
//        AppExecutors.getInstance().mainThread().execute(new Runnable() {
//            @Override
//            public void run() {
//                getBusiness(businessId);
//            }
//        });
    }
}
