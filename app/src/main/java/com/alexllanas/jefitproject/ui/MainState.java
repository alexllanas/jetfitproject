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
}
