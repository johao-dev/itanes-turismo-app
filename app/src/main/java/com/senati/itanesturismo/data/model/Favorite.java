package com.senati.itanesturismo.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "favorites", primaryKeys = {"tourist_point_id", "user_id"})
public class Favorite {

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "tourist_point_id")
    private int touristPointId;
}
