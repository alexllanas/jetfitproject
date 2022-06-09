package com.alexllanas.jefitproject.features.city;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.List;

public class CityWithBusinesses {
    @Embedded
    public City city;

    @Relation(
            parentColumn = "cityId",
            entityColumn = "businessId"
    )
    public List<Business> businesses;
}
