package com.alexllanas.jefitproject.data.db;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alexllanas.jefitproject.features.city.City;

import java.util.List;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM reviews  WHERE name = :cityName")
   City getCity(String cityName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCity(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertCities(List<City> cities);


    @Update
    int updateCity(City city);

}
