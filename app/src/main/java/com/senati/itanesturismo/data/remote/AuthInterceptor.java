package com.senati.itanesturismo.data.remote;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private final TokenManager tokenManager;

    public AuthInterceptor(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = tokenManager.getToken();

        if (token == null) {
            return chain.proceed(originalRequest);
        }

        Request newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer " + token)
            .build();

        return chain.proceed(newRequest);
    }
}
