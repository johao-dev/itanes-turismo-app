package com.senati.itanesturismo.ui.favorites;

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
import com.senati.itanesturismo.utils.JwtUtils;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private final FavoriteRepository repository;
    private final TokenManager tokenManager;

    private final MutableLiveData<List<TouristPoint>> favorites = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        this.repository = new FavoriteRepository(application, db);
        this.tokenManager = new TokenManager(application);
    }

    public LiveData<List<TouristPoint>> getFavorites() {
        return favorites;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void loadFavorites() {
        String token = tokenManager.getToken();
        int userId = JwtUtils.extractUserIdFromJwt(token);

        if (userId == -1) {
            error.setValue("Error de autenticación. Por favor, inicia sesión nuevamente.");
            return;
        }

        isLoading.setValue(true);

        repository.getFavoritesByUserId(userId, new RepositoryCallback<List<TouristPoint>>() {
            @Override
            public void onSuccess(List<TouristPoint> data) {
                favorites.postValue(data);
                isLoading.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue("Error al cargar favoritos: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }
}
