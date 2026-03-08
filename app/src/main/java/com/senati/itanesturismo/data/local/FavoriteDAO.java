package com.senati.itanesturismo.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
        WHERE f.user_id = :userId AND f.sync_status != 2
    """)
    List<TouristPoint> findAllFavoritesByUserId(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    @Query("SELECT * FROM favorites WHERE sync_status = :status")
    List<Favorite> getFavoritesBySyncStatus(int status);

    @Query("UPDATE favorites SET sync_status = :status WHERE tourist_point_id = :touristPointId AND user_id = :userId")
    void updateFavoriteSyncStatus(int touristPointId, int userId, int status);

    @Query("""
        DELETE FROM favorites
        WHERE tourist_point_id = :touristPointId
        AND user_id = :userId
    """)
    void deletePermanently(int touristPointId, int userId);

    @Query("SELECT * FROM favorites WHERE tourist_point_id = :touristPointId AND user_id = :userId")
    Favorite getFavorite(int touristPointId, int userId);
}
