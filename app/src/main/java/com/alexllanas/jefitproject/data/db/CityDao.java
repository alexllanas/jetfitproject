package com.alexllanas.jefitproject.data.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.features.city.CityWithBusinesses;

import java.util.List;

import io.reactivex.rxjava3.core.Single;


@Dao
public interface CityDao {
    @Query("SELECT * FROM cities")
    LiveData<CityWithBusinesses> getCities();

    @Query("SELECT * FROM cities  WHERE name = :cityName")
    LiveData<CityWithBusinesses> getCities(String cityName);

    @Insert
    Integer insertCity(City city);

    @Update
    Integer updateCity(City city);

    @Transaction
    @Update
    default void update(CityWithBusinesses cityWithBusinesses) {
        updateCity(cityWithBusinesses.city);
    }

}
