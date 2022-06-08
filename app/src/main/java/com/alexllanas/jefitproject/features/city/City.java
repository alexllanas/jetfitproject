package com.alexllanas.jefitproject.features.city;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.ArrayList;

public class City {
    public String name;
    public ArrayList<Business> businessList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Business> getBusinessList() {
        return businessList;
    }

    public void setBusinessList(ArrayList<Business> businessList) {
        this.businessList = businessList;
    }

}
