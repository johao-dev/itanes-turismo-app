package com.senati.itanesturismo.data.local;

import androidx.room.Dao;
import androidx.room.Query;

import com.senati.itanesturismo.data.model.TouristPoint;

import java.util.List;

@Dao
public interface TouristPointDAO {

    @Query("SELECT * FROM tourist_points")
    List<TouristPoint> findAll();

    @Query("""
        SELECT tp.* FROM tourist_points tp
        INNER JOIN favorites f ON tp.id = f.tourist_point_id
        WHERE f.user_id = :userId
    """)
    List<TouristPoint> findAllFavoritesByUserId(int userId);
}
