package com.senati.itanesturismo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.senati.itanesturismo.R;
import com.senati.itanesturismo.data.remote.TokenManager;
import com.senati.itanesturismo.databinding.ActivityLoginBinding;
import com.senati.itanesturismo.ui.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TokenManager tokenManager = new TokenManager(this);
        if (tokenManager.getToken() != null) {
            navigateToMain();
            return;
        }
        EdgeToEdge.enable(this);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        setupObservers();
        setupClickListeners();
    }

    private void setupObservers() {
        viewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess != null && isSuccess) {
                navigateToMain();
            }
        });

        viewModel.getIsLoading().observe(this, isLoading -> {
            binding.btnLogin.setEnabled(!isLoading);
            binding.btnLogin.setText(isLoading ? "Cargando..." : "Iniciar Sesión");
        });

        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            String username = binding.etuser.getText().toString();
            String password = binding.etpassword.getText().toString();

            viewModel.login(username, password);
        });
    }

    private void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}