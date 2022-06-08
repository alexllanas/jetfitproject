package com.alexllanas.jefitproject.data.network;

import io.reactivex.rxjava3.core.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpApiService {

    @GET("business/search")
    Flowable<SearchResponse> getBusinesses(
            @Header("Authorization") String token,
            @Query("location") String city
    );

    @GET("/businesses/{id}/reviews")
    Flowable<ReviewResponse> getReviews(
            @Header("Authorization") String token,
            @Path("id") String id
    );

}
