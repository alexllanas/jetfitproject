package com.alexllanas.jefitproject.features.business;

import com.alexllanas.jefitproject.features.detail.Review;

import java.util.ArrayList;
import java.util.Map;

public class Business {
    public int id;
    public String name = "";
    public int rating;
    public ArrayList<Map<String, String>> categories;
    public Location location;
    public ArrayList<Review> reviews;
    public boolean isLiked;
    public String imageUrl = "";

    public Business(int id, String name, int rating, ArrayList<Map<String, String>> categories, Location location, ArrayList<Review> reviews, boolean isLiked, String imageUrl) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.categories = categories;
        this.location = location;
        this.reviews = reviews;
        this.isLiked = isLiked;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<Map<String, String>> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Map<String, String>> categories) {
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
