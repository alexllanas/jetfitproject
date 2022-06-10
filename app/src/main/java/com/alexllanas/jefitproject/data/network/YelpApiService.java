package com.alexllanas.jefitproject.data.network;

import androidx.lifecycle.LiveData;

import com.alexllanas.jefitproject.data.network.utils.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApiService {

    @GET("businesses/search?limit=10")
    LiveData<ApiResponse<SearchResponse>> getBusinesses(
            @Header("Authorization") String token,
            @Query("location") String city
    );

    @GET("businesses/{id}/reviews?limit=3")
    LiveData<ApiResponse<ReviewResponse>> getReviews(
            @Header("Authorization") String token,
            @Path("id") String id
    );

}
