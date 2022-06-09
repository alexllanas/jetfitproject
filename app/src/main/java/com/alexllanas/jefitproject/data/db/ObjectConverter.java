package com.alexllanas.jefitproject.data.db;

import androidx.room.TypeConverter;

import com.alexllanas.jefitproject.features.business.Location;
import com.alexllanas.jefitproject.features.detail.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {

    @TypeConverter
    public static String fromStringArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static ArrayList<String> toStringArrayList(String json) {
        Type entityType = new TypeToken<ArrayList<String>>() {
        }.getType();
        return new Gson().fromJson(json, entityType);
    }

    @TypeConverter
    public static String fromReviewList(ArrayList<Review> reviews) {
        Gson gson = new Gson();
        return gson.toJson(reviews);
    }

    @TypeConverter
    public static ArrayList<Review> toReviewList(String json) {
        Type entityType = new TypeToken<ArrayList<Review>>() {
        }.getType();
        return new Gson().fromJson(json, entityType);
    }

    @TypeConverter
    public static String fromLocation(Location location) {
        Gson gson = new Gson();
        return gson.toJson(location);
    }

    @TypeConverter
    public static Location toLocation(String json) {
        Type entityType = new TypeToken<Location>() {
        }.getType();
        return new Gson().fromJson(json, entityType);
    }
}
