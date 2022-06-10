package com.alexllanas.jefitproject.features.business;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.alexllanas.jefitproject.features.detail.Review;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


@Entity(tableName = "businesses")
public class Business implements Parcelable {

    @NonNull
    @PrimaryKey
    @SerializedName("id")
    public String businessId;
    public String name = "";
    public String rating;
    public ArrayList<Map<String, String>> categories;
    public Location location;
    public ArrayList<Review> reviews;
    public boolean isLiked;
    @SerializedName("image_url")
    public String imageUrl = "";
    public Coordinates coordinates;

    public Business() {
    }

    public Business(@NonNull String businessId, String name, String rating, ArrayList<Map<String, String>> categories, Location location, ArrayList<Review> reviews, boolean isLiked, String imageUrl, Coordinates coordinates) {
        this.businessId = businessId;
        this.name = name;
        this.rating = rating;
        this.categories = categories;
        this.location = location;
        this.reviews = reviews;
        this.isLiked = isLiked;
        this.imageUrl = imageUrl;
        this.coordinates = coordinates;
    }


    protected Business(Parcel in) {
        businessId = in.readString();
        name = in.readString();
        rating = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
        isLiked = in.readByte() != 0;
        imageUrl = in.readString();
    }

    public static final Creator<Business> CREATOR = new Creator<Business>() {
        @Override
        public Business createFromParcel(Parcel in) {
            return new Business(in);
        }

        @Override
        public Business[] newArray(int size) {
            return new Business[size];
        }
    };

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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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

    public void from(Business business) {
        this.businessId = business.businessId;
        this.name = business.name;
        this.rating = business.rating;
        this.categories = business.categories;
        this.location = business.location;
        this.reviews = business.reviews;
        this.isLiked = business.isLiked;
        this.imageUrl = business.imageUrl;
        this.coordinates = business.coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Business business = (Business) o;
        return isLiked == business.isLiked && businessId.equals(business.businessId) && Objects.equals(name, business.name) && Objects.equals(rating, business.rating) && Objects.equals(categories, business.categories) && Objects.equals(location, business.location) && Objects.equals(reviews, business.reviews) && Objects.equals(imageUrl, business.imageUrl) && Objects.equals(coordinates, business.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId, name, rating, categories, location, reviews, isLiked, imageUrl, coordinates);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.businessId);
        dest.writeString(this.name);
        dest.writeString(this.rating);
        dest.writeString(this.imageUrl);
        dest.writeBoolean(this.isLiked);
        dest.writeList(this.reviews);
        dest.writeParcelable(this.location, 0);
        dest.writeList(categories);
        dest.writeParcelable(this.coordinates, 0);
    }
}
