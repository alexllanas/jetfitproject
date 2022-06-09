package com.alexllanas.jefitproject.di;

import android.app.Application;

import androidx.room.Room;

import com.alexllanas.jefitproject.data.db.CityDao;
import com.alexllanas.jefitproject.data.db.JefitDatabase;
import com.alexllanas.jefitproject.data.network.LiveDataCallAdapterFactory;
import com.alexllanas.jefitproject.data.network.YelpApiService;
import com.alexllanas.jefitproject.features.city.CityRepo;
import com.alexllanas.jefitproject.util.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Singleton
    @Provides
    public static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
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
    static CityDao provideBookDao(JefitDatabase database) {
        return database.cityDao();
    }

    @Singleton
    @Provides
    public static CityRepo provideCityRepo(YelpApiService yelpApiService, CityDao cityDao) {
        return new CityRepo(yelpApiService, cityDao);
    }
}
