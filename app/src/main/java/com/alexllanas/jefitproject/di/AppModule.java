package com.alexllanas.jefitproject.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.alexllanas.jefitproject.data.db.BusinessDao;
import com.alexllanas.jefitproject.data.db.CityDao;
import com.alexllanas.jefitproject.data.db.JefitDatabase;
import com.alexllanas.jefitproject.data.network.utils.LiveDataCallAdapterFactory;
import com.alexllanas.jefitproject.data.network.utils.NetworkInterceptor;
import com.alexllanas.jefitproject.data.network.YelpApiService;
import com.alexllanas.jefitproject.ui.MainRepo;
import com.alexllanas.jefitproject.util.Constants;
import com.alexllanas.jefitproject.util.NetworkHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Singleton
    @Provides
    public static NetworkHelper provideNetworkHelper(@ApplicationContext Context context) {
        return new NetworkHelper(context);
    }


    @Singleton
    @Provides
    public static OkHttpClient provideOKHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor())
                .build();
    }

    @Singleton
    @Provides
    public static Retrofit provideRetrofitInstance(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public static YelpApiService provideYelpApiService(Retrofit retrofit) {
        return retrofit.create(YelpApiService.class);
    }

    @Singleton
    @Provides
    static JefitDatabase provideJefitDatabase(Application application) {
        return Room.databaseBuilder(
                application,
                JefitDatabase.class,
                Constants.DATABASE_NAME
        ).build();
    }

    @Singleton
    @Provides
    static CityDao provideCityDao(JefitDatabase database) {
        return database.cityDao();
    }

    @Singleton
    @Provides
    static BusinessDao provideBusinessDao(JefitDatabase database) {
        return database.businessDao();
    }

    @Singleton
    @Provides
    public static MainRepo provideCityRepo(YelpApiService yelpApiService, CityDao cityDao, BusinessDao businessDao, NetworkHelper networkHelper) {
        return new MainRepo(yelpApiService, cityDao, businessDao, networkHelper);
    }

}
