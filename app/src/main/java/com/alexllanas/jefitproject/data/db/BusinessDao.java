package com.alexllanas.jefitproject.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alexllanas.jefitproject.features.business.Business;

import java.util.List;

@Dao
public interface BusinessDao {

    @Query("SELECT * FROM businesses")
    LiveData<Business> getBusinesses();

    @Query("SELECT * FROM businesses  WHERE businessId = :businessId")
    Business getBusiness(String businessId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertBusiness(List<Business> businesses);

    @Update
    int updateBusiness(Business business);
}
