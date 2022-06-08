package com.alexllanas.jefitproject.data.network;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.ArrayList;

public class SearchResponse {
    public ArrayList<Business> businesses;
    public int total;

    public SearchResponse(ArrayList<Business> businesses, int total) {
        this.businesses = businesses;
        this.total = total;
    }

    public ArrayList<Business> getBusinesses() {
        return businesses;
    }

    public int getTotal() {
        return total;
    }
}
