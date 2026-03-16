package com.senati.itanesturismo.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.senati.itanesturismo.databinding.ActivityFavoriteBinding;
import com.senati.itanesturismo.ui.adapters.TouristPointAdapter;
import com.senati.itanesturismo.ui.detail.DetailActivity;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private ActivityFavoriteBinding binding;
    private TouristPointAdapter adapter;
    private FavoriteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupViewModel();
    }

    private void setupRecyclerView() {
        binding.recyclerFavoritos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TouristPointAdapter(new ArrayList<>(), touristPoint ->{
            Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
            intent.putExtra("TOURIST_POINT_ID", touristPoint.getId());
            startActivity(intent);
        });

        binding.recyclerFavoritos.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        viewModel.getFavorites().observe(this, favorites -> {
            if (favorites != null) {
                adapter.updateData(favorites);
            }
        });

        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.loadFavorites();
    }
}