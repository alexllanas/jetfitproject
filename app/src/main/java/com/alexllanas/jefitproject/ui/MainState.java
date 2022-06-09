package com.alexllanas.jefitproject.ui;

import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.features.detail.Review;

import java.util.ArrayList;

public class MainState {
    public ArrayList<City> cityList = new ArrayList<>();
    public ArrayList<Business> businessList = new ArrayList<>();
    public ArrayList<Review> reviewList = new ArrayList<>();
    public Business business;
    public boolean isLoading;


    public MainState() {
    }

    public MainState(ArrayList<City> cityList, ArrayList<Business> businessList, ArrayList<Review> reviewList, Business business, boolean isLoading) {
        this.cityList = cityList;
        this.businessList = businessList;
        this.reviewList = reviewList;
        this.business = business;
        this.isLoading = isLoading;
    }

    public ArrayList<City> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<City> cityList) {
        this.cityList = cityList;
    }

    public ArrayList<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(ArrayList<Business> businessList) {
        this.businessList = businessList;
    }

    public ArrayList<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(ArrayList<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public MainState copy() {
        return new MainState(cityList, businessList, reviewList, business, isLoading);
    }

}
