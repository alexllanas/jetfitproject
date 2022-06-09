package com.alexllanas.jefitproject.data.network;

import com.alexllanas.jefitproject.features.business.Business;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("businesses")
    @Expose
    public List<Business> businesses;

    @SerializedName("total")
    @Expose
    public int total;

    public SearchResponse(List<Business> businesses, int total) {
        this.businesses = businesses;
        this.total = total;
    }

    public List<Business> getBusinesses() {
        return businesses;
    }

    public int getTotal() {
        return total;
    }
}
