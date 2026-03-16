package com.senati.itanesturismo.ui.detail;

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

public class DetailViewModel extends AndroidViewModel {

    private final MutableLiveData<TouristPoint> lugar = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavorito = new MutableLiveData<>(false);

    private final FavoriteRepository favoriteRepository;
    private final TouristPointRepository touristPointRepository;
    private final TokenManager tokenManager;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        this.favoriteRepository = new FavoriteRepository(application, db);
        this.touristPointRepository = new TouristPointRepository(application, db);
        this.tokenManager = new TokenManager(application);
    }

    public LiveData<TouristPoint> getLugar() { return lugar; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getIsFavorito() { return isFavorito; }

    public void setLugar(TouristPoint point) {
        lugar.setValue(point);
        checkFavorito();
    }

    public void loadTouristPointById(int id) {
        isLoading.setValue(true);

        touristPointRepository.getTouristPointById(id, new RepositoryCallback<TouristPoint>() {
            @Override
            public void onSuccess(TouristPoint data) {
                lugar.postValue(data);
                isLoading.postValue(false);
                checkFavorito();
            }

            @Override
            public void onError(Throwable t) {
                error.postValue("Error al cargar punto turístico: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    private void checkFavorito() {
        TouristPoint current = lugar.getValue();
        if (current == null) return;

        String token = tokenManager.getToken();
        int userId = JwtUtils.extractUserIdFromJwt(token);
        if (userId == -1) return;

        favoriteRepository.getFavoritesByUserId(userId, new RepositoryCallback<List<TouristPoint>>() {
            @Override
            public void onSuccess(List<TouristPoint> data) {
                boolean fav = data.stream().anyMatch(tp -> tp.getId() == current.getId());
                isFavorito.postValue(fav);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue("Error al obtener favoritos: " + t.getMessage());
            }
        });
    }

    public void toggleFavorito() {
        TouristPoint current = lugar.getValue();
        if (current == null) return;

        String token = tokenManager.getToken();
        int userId = JwtUtils.extractUserIdFromJwt(token);
        if (userId == -1) {
            error.setValue("Error de autenticación. Por favor, inicia sesión nuevamente.");
            return;
        }

        isLoading.setValue(true);

        favoriteRepository.getFavoritesByUserId(userId, new RepositoryCallback<List<TouristPoint>>() {
            @Override
            public void onSuccess(List<TouristPoint> data) {
                boolean esFavorito = data.stream()
                        .anyMatch(tp -> tp.getId() == current.getId());

                if (esFavorito) {
                    favoriteRepository.removeFavorite(userId, current.getId());
                    isFavorito.postValue(false);
                } else {
                    favoriteRepository.addFavorite(userId, current.getId());
                    isFavorito.postValue(true);
                }

                isLoading.postValue(false);
            }

            @Override
            public void onError(Throwable t) {
                error.postValue("Error al actualizar favorito: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }
}