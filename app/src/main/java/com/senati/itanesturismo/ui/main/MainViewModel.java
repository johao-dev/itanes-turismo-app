package com.senati.itanesturismo.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senati.itanesturismo.data.remote.TokenManager;
import com.senati.itanesturismo.utils.JwtUtils;

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
            String username = JwtUtils.extractUsernameFromJwt(token);
            usernameLiveData.setValue(username);
        } else {
            usernameLiveData.setValue("Viajero");
        }
    }
}
