package com.alexllanas.jefitproject.features.city;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CityWithBusinesses {

    public CityWithBusinesses(City city, ArrayList<Business> businesses) {
        this.city = city;
        this.businesses = businesses;
    }

    @Embedded
    public City city;

    @Relation(
            parentColumn = "cityId",
            entityColumn = "businessId"
    )
    public ArrayList<Business> businesses;
}
