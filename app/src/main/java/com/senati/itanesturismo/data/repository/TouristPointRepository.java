package com.senati.itanesturismo.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.senati.itanesturismo.data.local.AppDatabase;
import com.senati.itanesturismo.data.local.LocalDataSource;
import com.senati.itanesturismo.data.model.TouristPoint;
import com.senati.itanesturismo.data.remote.RemoteDataSource;
import com.senati.itanesturismo.data.remote.dto.JSendResponse;
import com.senati.itanesturismo.data.remote.dto.TouristPointData;
import com.senati.itanesturismo.data.remote.dto.TouristPointMapper;
import com.senati.itanesturismo.data.remote.dto.TouristPointResponse;
import com.senati.itanesturismo.utils.NetworkUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TouristPointRepository {

    private final Context context;
    private final LocalDataSource local;
    private final RemoteDataSource remote;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public TouristPointRepository(Context context, AppDatabase db) {
        this.context = context;
        this.local = new LocalDataSource(db);
        this.remote = new RemoteDataSource(context);
    }

    public void getTouristPoints(RepositoryCallback<List<TouristPoint>> callback) {
        if (NetworkUtils.isOnline(context)) {
            remote.getTouristPoints().enqueue(new Callback<JSendResponse<TouristPointData>>() {
                @Override
                public void onResponse(
                    Call<JSendResponse<TouristPointData>> call,
                    Response<JSendResponse<TouristPointData>> response
                ) {
                    if (response.isSuccessful()) {
                        List<TouristPointResponse> dtos = response.body().data().touristPoints();
                        List<TouristPoint> entities =
                            dtos.stream()
                                .map(TouristPointMapper::toEntity)
                                .collect(Collectors.toList());

                        executor.execute(() -> {
                            local.saveTouristPoints(entities);
                        });

                        callback.onSuccess(entities);
                    }
                }

                @Override
                public void onFailure(Call<JSendResponse<TouristPointData>> call, Throwable t) {
                    executor.execute(() -> {
                        List<TouristPoint> localData = local.getTouristPoints();
                        new Handler(Looper.getMainLooper()).post(() -> {
                            callback.onSuccess(localData);
                        });
                    });
                }
            });
        } else {
            executor.execute(() -> {
                List<TouristPoint> localData = local.getTouristPoints();
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(localData);
                });
            });
        }
    }

    public void getTouristPointById(int id, RepositoryCallback<TouristPoint> callback) {
        if (NetworkUtils.isOnline(context)) {
            remote.getTouristPointById(id).enqueue(new Callback<JSendResponse<TouristPointResponse>>() {
                @Override
                public void onResponse(
                    Call<JSendResponse<TouristPointResponse>> call,
                    Response<JSendResponse<TouristPointResponse>> response
                ) {
                    if (response.isSuccessful()) {
                        TouristPointResponse dto = response.body().data();
                        TouristPoint entity = TouristPointMapper.toEntity(dto);

                        executor.execute(() -> {
                            local.saveTouristPoint(entity);
                        });
                        callback.onSuccess(entity);
                    }
                }

                @Override
                public void onFailure(
                    Call<JSendResponse<TouristPointResponse>> call,
                    Throwable t
                ) {
                    executor.execute(() -> {
                        TouristPoint localData = local.getTouristPointById(id);
                        new Handler(Looper.getMainLooper()).post(() -> {
                            callback.onSuccess(localData);
                        });
                    });
                }
            });
        } else {
            executor.execute(() -> {
                TouristPoint localData = local.getTouristPointById(id);
                new Handler(Looper.getMainLooper()).post(() -> {
                    callback.onSuccess(localData);
                });
            });
        }
    }

    public void syncTouristPoints() {
        if (!NetworkUtils.isOnline(context)) return;

        remote.getTouristPoints().enqueue(new Callback<JSendResponse<TouristPointData>>() {
            @Override
            public void onResponse(
                Call<JSendResponse<TouristPointData>> call,
                Response<JSendResponse<TouristPointData>> response
            ) {
                if (response.isSuccessful()) {
                    List<TouristPoint> entities =
                        response.body().data().touristPoints().stream()
                            .map(TouristPointMapper::toEntity)
                            .collect(Collectors.toList());

                    executor.execute(() -> {
                        local.saveTouristPoints(entities);
                    });
                }
            }

            @Override
            public void onFailure(Call<JSendResponse<TouristPointData>> call, Throwable t) { }
        });
    }
}
