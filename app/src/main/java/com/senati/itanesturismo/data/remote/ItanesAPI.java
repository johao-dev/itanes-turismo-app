package com.senati.itanesturismo.data.remote;

import com.senati.itanesturismo.data.remote.dto.AddFavoriteRequest;
import com.senati.itanesturismo.data.remote.dto.FavoritesData;
import com.senati.itanesturismo.data.remote.dto.JSendResponse;
import com.senati.itanesturismo.data.remote.dto.LoginRequest;
import com.senati.itanesturismo.data.remote.dto.LoginData;
import com.senati.itanesturismo.data.remote.dto.MessageData;
import com.senati.itanesturismo.data.remote.dto.TouristPointData;
import com.senati.itanesturismo.data.remote.dto.TouristPointResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ItanesAPI {

    @POST("users/login")
    Call<JSendResponse<LoginData>> login(@Body LoginRequest loginRequest);

    @GET("tourist-points")
    Call<JSendResponse<TouristPointData>> getTouristPoints();

    @GET("tourist-points/{id}")
    Call<JSendResponse<TouristPointResponse>> getTouristPointById(@Path("id") Integer id);

    @GET("favorites")
    Call<JSendResponse<FavoritesData>> getFavorites();

    @POST("favorites")
    Call<JSendResponse<MessageData>> addFavorite(@Body AddFavoriteRequest addFavoriteRequest);

    @DELETE("favorites/{touristPointId}")
    Call<JSendResponse<MessageData>> deleteFavorite(@Path("touristPointId") Integer touristPointId);
}
