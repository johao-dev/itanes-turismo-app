package com.senati.itanesturismo.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.senati.itanesturismo.data.model.TouristPoint;

import java.util.List;

@Dao
public interface TouristPointDAO {

    @Query("SELECT * FROM tourist_points")
    List<TouristPoint> findAll();

    @Query("SELECT * FROM tourist_points WHERE id = :id")
    TouristPoint findById(int id);

    @Update
    void update(TouristPoint touristPoint);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TouristPoint> touristPoints);
}
