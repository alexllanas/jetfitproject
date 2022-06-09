package com.alexllanas.jefitproject.data.db;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.alexllanas.jefitproject.features.business.Business;
import com.alexllanas.jefitproject.features.city.City;
import com.alexllanas.jefitproject.util.StaticData;

@Database(entities = {City.class, Business.class}, version = 1)
@TypeConverters(ObjectConverter.class)
public abstract class JefitDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    public abstract BusinessDao businessDao();
}
