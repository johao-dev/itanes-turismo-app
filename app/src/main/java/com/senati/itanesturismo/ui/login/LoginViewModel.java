package com.senati.itanesturismo.ui.login;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.senati.itanesturismo.data.remote.TokenManager;
import com.senati.itanesturismo.data.repository.AuthRepository;
import com.senati.itanesturismo.data.repository.RepositoryCallback;

public class LoginViewModel extends AndroidViewModel {

    private final AuthRepository repository;
    private final TokenManager tokenManager;

    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.repository = new AuthRepository(application);
        this.tokenManager = new TokenManager(application);
    }

    public LiveData<Boolean> getLoginSuccess() { return loginSuccess; }
    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }

    public void login(String username, String password) {
        boolean validationFailed =
            username == null ||
            username.trim().isEmpty() ||
            password == null ||
            password.trim().isEmpty();

        if (validationFailed) {
            error.setValue("Por favor, ingrese un nombre de usuario y contraseña válidos.");
            return;
        }

        isLoading.setValue(true);

        repository.login(username, password, new RepositoryCallback<String>() {
            @Override
            public void onSuccess(String token) {
                tokenManager.saveToken(token);

                isLoading.postValue(false);
                loginSuccess.postValue(true);
            }

            @Override
            public void onError(Throwable t) {
                isLoading.postValue(false);
                error.postValue(t.getMessage());
            }
        });
    }
}
