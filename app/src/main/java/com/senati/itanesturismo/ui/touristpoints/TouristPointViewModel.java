package com.senati.itanesturismo.ui.touristpoints;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senati.itanesturismo.data.local.AppDatabase;
import com.senati.itanesturismo.data.model.TouristPoint;
import com.senati.itanesturismo.data.remote.TokenManager;
import com.senati.itanesturismo.data.repository.FavoriteRepository;
import com.senati.itanesturismo.data.repository.RepositoryCallback;
import com.senati.itanesturismo.data.repository.TouristPointRepository;
import com.senati.itanesturismo.utils.JwtUtils;

import java.util.List;

public class TouristPointViewModel extends AndroidViewModel {

    private final MutableLiveData<List<TouristPoint>> touristPoints = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final TouristPointRepository repository;


    public TouristPointViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        this.repository = new TouristPointRepository(application, db);
    }

    public LiveData<List<TouristPoint>> getTouristPoints() {
        return touristPoints;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadTouristPoints() {
        isLoading.setValue(true);

        repository.getTouristPoints(new RepositoryCallback<List<TouristPoint>>() {
            @Override
            public void onSuccess(List<TouristPoint> data) {
                touristPoints.postValue(data);
                isLoading.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue("Error al cargar puntos turísticos: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }
}
