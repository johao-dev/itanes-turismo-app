package com.senati.itanesturismo.ui.main;

import android.app.Application;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senati.itanesturismo.data.remote.TokenManager;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class MainViewModel extends AndroidViewModel {

    private final MutableLiveData<String> usernameLiveData = new MutableLiveData<>();
    private final TokenManager tokenManager;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.tokenManager = new TokenManager(application);
        loadUsername();
    }

    public LiveData<String> getUsername() {
        return usernameLiveData;
    }

    private void loadUsername() {
        String token = tokenManager.getToken();

        if (token != null && !token.isEmpty()) {
            String username = extractUsernameFromJwt(token);
            usernameLiveData.setValue(username);
        } else {
            usernameLiveData.setValue("Viajero");
        }
    }

    private String extractUsernameFromJwt(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length == 3) {
                String payloadBase64 = parts[1];

                byte[] decodedBytes = Base64.decode(payloadBase64, Base64.URL_SAFE);
                String payloadJson = new String(decodedBytes, StandardCharsets.UTF_8);

                JSONObject jsonObject = new JSONObject(payloadJson);
                if (jsonObject.has("username")) {
                    return jsonObject.getString("username");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Usuario";
    }
}
