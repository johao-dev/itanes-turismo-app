package com.senati.itanesturismo.data.remote;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://itanes-turismo-api-latest.onrender.com/api/v1";
    private static Retrofit retrofit;
    private static ItanesAPI api;

    public static ItanesAPI getApi(Context context) {
        if (retrofit == null) {
            TokenManager tokenManager = new TokenManager(context);
            AuthInterceptor authInterceptor = new AuthInterceptor(tokenManager);

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            api = retrofit.create(ItanesAPI.class);
        }

        return api;
    }
}
