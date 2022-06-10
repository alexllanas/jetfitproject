package com.alexllanas.jefitproject.data.network.utils;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    private static final String TAG = "NetworkInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request req = chain.request();
        Response res = chain.proceed(req);
        Log.d(TAG, res.peekBody(Long.MAX_VALUE).string());
        return res;
    }
}
