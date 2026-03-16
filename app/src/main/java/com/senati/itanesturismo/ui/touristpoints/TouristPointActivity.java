package com.senati.itanesturismo.ui.touristpoints;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.senati.itanesturismo.R;
import com.senati.itanesturismo.databinding.ActivityTouristPointBinding;
import com.senati.itanesturismo.ui.adapters.TouristPointAdapter;
import com.senati.itanesturismo.ui.detail.DetailActivity;

import java.util.ArrayList;

public class TouristPointActivity extends AppCompatActivity {

    private ActivityTouristPointBinding binding;
    private TouristPointAdapter adapter;
    private TouristPointViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTouristPointBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupRecyclerView();
        setupViewModel();
    }

    private void setupRecyclerView() {
        binding.recyclerRecorridos.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TouristPointAdapter(new ArrayList<>(), touristPoint -> {
            Intent intent = new Intent(TouristPointActivity.this, DetailActivity.class);
            intent.putExtra("TOURIST_POINT_ID", touristPoint.getId());
            startActivity(intent);
        });

        binding.recyclerRecorridos.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(TouristPointViewModel.class);

        viewModel.getTouristPoints().observe(this, touristPoints -> {
            if (touristPoints != null) {
                adapter.updateData(touristPoints);
            }
        });

        viewModel.getError().observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.loadTouristPoints();
    }
}