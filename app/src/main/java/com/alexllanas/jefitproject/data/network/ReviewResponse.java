package com.alexllanas.jefitproject.data.network;

import com.alexllanas.jefitproject.features.detail.Review;

import java.util.ArrayList;

public class ReviewResponse {
    public ArrayList<Review> reviews;
    public int total;

    public ReviewResponse(ArrayList<Review> reviews, int total) {
        this.reviews = reviews;
        this.total = total;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public int getTotal() {
        return total;
    }
}
