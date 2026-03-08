package com.senati.itanesturismo.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.senati.itanesturismo.data.model.Favorite;
import com.senati.itanesturismo.data.model.TouristPoint;

import java.util.List;

@Dao
public interface FavoriteDAO {

    @Query("""
        SELECT tp.* FROM tourist_points tp
        INNER JOIN favorites f ON tp.id = f.tourist_point_id
        WHERE f.user_id = :userId
    """)
    List<TouristPoint> findAllFavoritesByUserId(int userId);

    @Insert
    void insert(Favorite favorite);

    @Query("""
        DELETE FROM favorites
        WHERE tourist_point_id = :touristPointId
        AND user_id = :userId
    """)
    void deleteByIds(int touristPointId, int userId);
}
