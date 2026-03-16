package com.senati.itanesturismo.ui.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.senati.itanesturismo.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private DetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupViewModel();
        setupListeners();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        viewModel.getLugar().observe(this, touristPoint -> {
            if (touristPoint != null) {
                binding.txtTitulo.setText(touristPoint.getName());
                binding.txtDescripcion.setText(touristPoint.getDescription());

                // Glide para imagen
                Glide.with(this)
                        .load(touristPoint.getPhotoUrl())
                        .into(binding.imgLugar);
            }
        });

        viewModel.getIsFavorito().observe(this, isFav -> {
            binding.btnFavorito.setText(isFav ? "⭐ Guardado" : "⭐ Guardar a Favoritos");
        });

        viewModel.getError().observe(this, errorMsg -> {
            if (errorMsg != null && !errorMsg.isEmpty()) {
                Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getIsLoading().observe(this, loading -> {
            binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        });

        if (getIntent() != null && getIntent().hasExtra("TOURIST_POINT_ID")) {
            int touristPointId = getIntent().getIntExtra("TOURIST_POINT_ID", -1);
            if (touristPointId != -1) {
                viewModel.loadTouristPointById(touristPointId);
            } else {
                Toast.makeText(this, "ID de punto turístico inválido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupListeners() {
        binding.btnFavorito.setOnClickListener(v -> viewModel.toggleFavorito());

        binding.btnRuta.setOnClickListener(v -> {
            Toast.makeText(this, "Abrir mapa con ruta", Toast.LENGTH_SHORT).show();
        });

        binding.btnCompartir.setOnClickListener(v -> {
            Toast.makeText(this, "Compartir punto turístico", Toast.LENGTH_SHORT).show();
        });
    }
}