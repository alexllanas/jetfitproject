package com.alexllanas.jefitproject.util;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkHelper {
    private final Context context;

    public NetworkHelper(Context context) {
        this.context = context;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
