package com.alexllanas.jefitproject.features.city;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.ArrayList;

@Entity(tableName = "cities")
public class City {

    @NonNull
    @PrimaryKey()
    public String name = "";

    public ArrayList<String> businessIds = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
