package com.senati.itanesturismo.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.senati.itanesturismo.data.local.AppDatabase;
import com.senati.itanesturismo.data.local.LocalDataSource;
import com.senati.itanesturismo.data.model.Favorite;
import com.senati.itanesturismo.data.remote.RemoteDataSource;
import com.senati.itanesturismo.data.remote.dto.JSendResponse;
import com.senati.itanesturismo.data.remote.dto.MessageData;

import java.util.List;

import retrofit2.Response;

public class FavoriteSyncWorker extends Worker {

    private final LocalDataSource local;
    private final RemoteDataSource remote;

    public FavoriteSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        AppDatabase db = AppDatabase.getInstance(context);
        this.local = new LocalDataSource(db);
        this.remote = new RemoteDataSource(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        boolean hasErrors = false;

        // Sincroniza los pendientes de añadir
        List<Favorite> pendingAdds = local.getFavoritesBySyncStatus(Favorite.SYNC_STATUS_PENDING_ADD);
        for (Favorite fav : pendingAdds) {
            try {
                Response<JSendResponse<MessageData>> response = remote.addFavorite(fav.getTouristPointId()).execute();
                if (response.isSuccessful()) {
                    local.updateFavoriteSyncStatus(fav.getTouristPointId(), fav.getUserId(), Favorite.SYNC_STATUS_OK);
                } else {
                    hasErrors = true;
                }
            } catch (Exception e) {
                hasErrors = true;
            }
        }

        // Sincroniza los pendientes de eliminar
        List<Favorite> pendingDeletes = local.getFavoritesBySyncStatus(Favorite.SYNC_STATUS_PENDING_DELETE);
        for (Favorite fav : pendingDeletes) {
            try {
                Response<JSendResponse<MessageData>> response = remote.deleteFavorite(fav.getTouristPointId()).execute();
                if (response.isSuccessful() || response.code() == 404) {
                    local.removeFavorite(fav.getTouristPointId(), fav.getUserId());
                } else {
                    hasErrors = true;
                }
            } catch (Exception e) {
                hasErrors = true;
            }
        }

        if (hasErrors) return Result.retry();
        return Result.success();
    }
}
