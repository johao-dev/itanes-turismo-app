package com.senati.itanesturismo.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.senati.itanesturismo.data.local.AppDatabase;
import com.senati.itanesturismo.data.local.LocalDataSource;
import com.senati.itanesturismo.data.model.Favorite;
import com.senati.itanesturismo.data.model.TouristPoint;
import com.senati.itanesturismo.data.remote.RemoteDataSource;
import com.senati.itanesturismo.data.remote.dto.FavoritesData;
import com.senati.itanesturismo.data.remote.dto.JSendResponse;
import com.senati.itanesturismo.data.remote.dto.MessageData;
import com.senati.itanesturismo.data.remote.dto.TouristPointMapper;
import com.senati.itanesturismo.utils.NetworkUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteRepository {

    private final Context context;
    private final LocalDataSource local;
    private final RemoteDataSource remote;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public FavoriteRepository(Context context, AppDatabase db) {
        this.context = context;
        this.local = new LocalDataSource(db);
        this.remote = new RemoteDataSource(context);
    }

    public void getFavoritesByUserId(int userId, RepositoryCallback<List<TouristPoint>> callback) {
        if (NetworkUtils.isOnline(context)) {
            remote.getFavorites().enqueue(new Callback<JSendResponse<FavoritesData>>() {
                @Override
                public void onResponse(
                        Call<JSendResponse<FavoritesData>> call,
                        Response<JSendResponse<FavoritesData>> response
                ) {
                    if (response.isSuccessful()) {
                        List<TouristPoint> entities = response.body().data().favorites().stream()
                                .map(TouristPointMapper::toEntity)
                                .collect(Collectors.toList());

                        executor.execute(() -> {
                            local.saveTouristPoints(entities);

                            for (TouristPoint tp : entities) {
                                local.addFavorite(new Favorite(userId, tp.getId()));
                            }
                        });
                        callback.onSuccess(entities);
                    }
                }

                @Override
                public void onFailure(
                        Call<JSendResponse<FavoritesData>> call,
                        Throwable t
                ) {
                    executor.execute(() -> {
                        List<TouristPoint> localData = local.getFavoritesByUserId(userId);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            callback.onSuccess(localData);
                        });
                    });
                }
            });
        } else {
            executor.execute(() -> {
                List<TouristPoint> localData = local.getFavoritesByUserId(userId);
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(localData);
                });
            });
        }
    }

    public void addFavorite(int userId, int touristPointId) {
        Favorite favorite = new Favorite(userId, touristPointId);

        executor.execute(() -> {
            local.addFavorite(favorite);
        });

        if (NetworkUtils.isOnline(context)) {
            remote.addFavorite(touristPointId)
                .enqueue(new Callback<JSendResponse<MessageData>>() {
                    @Override
                    public void onResponse(
                        Call<JSendResponse<MessageData>> call,
                        Response<JSendResponse<MessageData>> response
                    ) { }

                    @Override
                    public void onFailure(
                        Call<JSendResponse<MessageData>> call,
                        Throwable t
                    ) { }
                }
            );
        }
    }

    public void removeFavorite(int userId, int touristPointId) {
        executor.execute(() -> {
            local.removeFavorite(userId, touristPointId);
        });

        if (NetworkUtils.isOnline(context)) {
            remote.deleteFavorite(touristPointId)
                .enqueue(new Callback<JSendResponse<MessageData>>() {
                    @Override
                    public void onResponse(
                        Call<JSendResponse<MessageData>> call,
                        Response<JSendResponse<MessageData>> response
                    ) { }

                    @Override
                    public void onFailure(
                        Call<JSendResponse<MessageData>> call,
                        Throwable t
                    ) { }
                }
            );
        }
    }
}
