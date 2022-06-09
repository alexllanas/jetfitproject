package com.alexllanas.jefitproject.features.city;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.ArrayList;

@Entity(tableName = "cities")
public class City {

    @NonNull
    @PrimaryKey
    public String name = "";

    public ArrayList<String> businessIds = new ArrayList<>();

    @Ignore
    public City(String name) {
        this.name = name;
    }

    public City(@NonNull String name, ArrayList<String> businessIds) {
        this.name = name;
        this.businessIds = businessIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(ArrayList<String> businessIds) {
        this.businessIds = businessIds;
    }
}
