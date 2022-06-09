package com.alexllanas.jefitproject.data.network;

import androidx.lifecycle.LiveData;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApiService {

    @GET("business/search")
    LiveData<ApiResponse<SearchResponse>> getBusinesses(
            @Header("Authorization") String token,
            @Query("location") String city
    );

    @GET("/businesses/{id}/reviews")
    LiveData<ApiResponse<ReviewResponse>> getReviews(
            @Header("Authorization") String token,
            @Path("id") String id
    );

}
