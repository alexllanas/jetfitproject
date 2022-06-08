package com.alexllanas.jefitproject.features.city;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.ArrayList;

@Entity
public class City {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name = "";

    public ArrayList<Business> businessList = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public City(String name, ArrayList<Business> businessList) {
        this.name = name;
        this.businessList = businessList;
    }

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
