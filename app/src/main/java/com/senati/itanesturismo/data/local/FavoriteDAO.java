package com.senati.itanesturismo.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

import com.senati.itanesturismo.data.model.Favorite;

@Dao
public interface FavoriteDAO {

    @Insert
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);
}
