package com.alexllanas.jefitproject.features.business;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexllanas.jefitproject.features.detail.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

@Entity(tableName = "businesses")
public class Business {


    @NonNull
    @PrimaryKey
    @SerializedName("id")
    public String businessId;

    public String name = "";
    public int rating;
    public ArrayList<String> categories;
    public Location location;
    public ArrayList<Review> reviews;
    public boolean isLiked;
    @SerializedName("image_url")
    public String imageUrl = "";

    public Business(String businessId, String name, int rating, ArrayList<String> categories, Location location, ArrayList<Review> reviews, boolean isLiked, String imageUrl) {
        this.businessId = businessId;
        this.name = name;
        this.rating = rating;
        this.categories = categories;
        this.location = location;
        this.reviews = reviews;
        this.isLiked = isLiked;
        this.imageUrl = imageUrl;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<String> categories) {
        this.categories = categories;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
