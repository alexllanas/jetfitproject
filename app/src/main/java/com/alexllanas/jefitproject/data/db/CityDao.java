package com.alexllanas.jefitproject.data.db;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.alexllanas.jefitproject.features.city.City;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CityDao {
    @Query("SELECT * FROM cities")
    List<City> getCities();

    @Query("SELECT * FROM cities  WHERE name = :cityName")
   City getCity(String cityName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertCities(List<City> cities);


    @Update
    int updateCity(City city);

}
