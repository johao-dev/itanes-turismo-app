package com.senati.itanesturismo.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "tourist_points")
public class TouristPoint {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;

    @ColumnInfo(name = "category_name")
    private String category;
    private String description;

    @ColumnInfo(name = "photo_url")
    private String photoUrl;

    @ColumnInfo(name = "tourist_point_address")
    private String touristPointAddress;
    private double latitude;
    private double longitude;

    @ColumnInfo(name = "is_synchronized")
    private boolean isSynchronized;
}
