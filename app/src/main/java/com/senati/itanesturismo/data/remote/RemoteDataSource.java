package com.senati.itanesturismo.data.remote;

import android.content.Context;

import com.senati.itanesturismo.data.remote.dto.AddFavoriteRequest;
import com.senati.itanesturismo.data.remote.dto.FavoritesData;
import com.senati.itanesturismo.data.remote.dto.JSendResponse;
import com.senati.itanesturismo.data.remote.dto.MessageData;
import com.senati.itanesturismo.data.remote.dto.TouristPointData;
import com.senati.itanesturismo.data.remote.dto.TouristPointResponse;

import retrofit2.Call;

public class RemoteDataSource {

    private final ItanesAPI api;

    public RemoteDataSource(Context context) {
        this.api = ApiClient.getApi(context);
    }

    public Call<JSendResponse<TouristPointData>> getTouristPoints() {
        return api.getTouristPoints();
    }

    public Call<JSendResponse<TouristPointResponse>> getTouristPointById(Integer id) {
        return api.getTouristPointById(id);
    }

    // La API guarda el ID del usuario en su contexto y no necesita recibirlo como parámetro
    public Call<JSendResponse<FavoritesData>> getFavorites() {
        return api.getFavorites();
    }

    public Call<JSendResponse<MessageData>> addFavorite(Integer touristPointId) {
        return api.addFavorite(new AddFavoriteRequest(touristPointId));
    }

    public Call<JSendResponse<MessageData>> deleteFavorite(Integer touristPointId) {
        return api.deleteFavorite(touristPointId);
    }
}
