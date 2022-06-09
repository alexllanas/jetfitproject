package com.alexllanas.jefitproject.data.db;


import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.CityWithBusinesses;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@Dao
public interface CityDao {
    @Query("SELECT * FROM cities")
    public List<CityWithBusinesses> getCities();

    @Update
    Single<Integer> updateCity(CityWithBusinesses city);
}
