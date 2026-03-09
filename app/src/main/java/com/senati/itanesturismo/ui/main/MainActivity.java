package com.senati.itanesturismo.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.senati.itanesturismo.databinding.ActivityMainBinding;

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
            //Intent intent = new Intent(MainActivity.this, FakeTouristPointActivity.class);
            //startActivity(intent);
        });

        binding.btnFavorites.setOnClickListener(v -> {
            //Intent intent = new Intent(MainActivity.this, FakeFavoriteActivity.class);
            //startActivity(intent);
        });
    }
}