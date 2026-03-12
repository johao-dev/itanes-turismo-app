package com.senati.itanesturismo.data.repository;

import android.content.Context;

import com.senati.itanesturismo.data.remote.ApiClient;
import com.senati.itanesturismo.data.remote.ItanesAPI;
import com.senati.itanesturismo.data.remote.dto.JSendResponse;
import com.senati.itanesturismo.data.remote.dto.LoginData;
import com.senati.itanesturismo.data.remote.dto.LoginRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthRepository {

    private final ItanesAPI api;

    public AuthRepository(Context context) {
        this.api = ApiClient.getApi(context);
    }

    public void login(String username, String password, RepositoryCallback<String> callback) {
        LoginRequest request = new LoginRequest(username, password);

        api.login(request).enqueue(new Callback<JSendResponse<LoginData>>() {
            @Override
            public void onResponse(
                Call<JSendResponse<LoginData>> call,
                Response<JSendResponse<LoginData>> response
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().data().token();
                    callback.onSuccess(token);
                } else {
                    callback.onError(new Exception("Usuario o contraseña incorrectos."));
                }
            }

            @Override
            public void onFailure(
                Call<JSendResponse<LoginData>> call,
                Throwable t
            ) {
                callback.onError(new Exception("Error de conexión al servidor"));
            }
        });
    }
}
