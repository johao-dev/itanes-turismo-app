package com.senati.itanesturismo.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.senati.itanesturismo.data.remote.TokenManager;
import com.senati.itanesturismo.databinding.ActivityMainBinding;
import com.senati.itanesturismo.ui.favorites.FavoriteActivity;
import com.senati.itanesturismo.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setupObservers();
        setupClickListeners();
    }

    private void setupObservers() {
        viewModel.getUsername().observe(this, username -> {
            String welcomeMessage = "¡Bienvenido de vuelta,\n" + username + "!";
            binding.tvWelcomeMessage.setText(welcomeMessage);
        });
    }

    // TODO: Reemplazar cuando los activities existan
    private void setupClickListeners() {
        binding.btnTouristPoints.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FakeTouristPointActivity.class);
            startActivity(intent);
        });

        binding.btnFavorites.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });

        binding.btnLogout.setOnClickListener(v -> {
            TokenManager tokenManager = new TokenManager(MainActivity.this);
            tokenManager.clearToken();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            finish();
        });
    }
}