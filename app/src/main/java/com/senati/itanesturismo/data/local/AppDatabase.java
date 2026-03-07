package com.senati.itanesturismo.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.senati.itanesturismo.data.model.Favorite;
import com.senati.itanesturismo.data.model.TouristPoint;

@Database(entities = { Favorite.class, TouristPoint.class }, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract FavoriteDAO favoriteDAO();
    public abstract TouristPointDAO touristPointDAO();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "itanes_local_db"
                    ).build();
                }
            }
        }

        return INSTANCE;
    }
}
