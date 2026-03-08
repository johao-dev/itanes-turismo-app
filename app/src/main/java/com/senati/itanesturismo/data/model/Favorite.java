package com.senati.itanesturismo.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "favorites", primaryKeys = {"tourist_point_id", "user_id"})
public class Favorite {

    public static final int SYNC_STATUS_OK = 0;
    public static final int SYNC_STATUS_PENDING_ADD = 1;
    public static final int SYNC_STATUS_PENDING_DELETE = 2;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "tourist_point_id")
    private int touristPointId;

    @Default
    @ColumnInfo(name = "sync_status")
    private int syncStatus = SYNC_STATUS_PENDING_ADD;
}
