package com.senati.itanesturismo.ui.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.senati.itanesturismo.data.model.TouristPoint;
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

            TouristPoint currentPoint = viewModel.getLugar().getValue();

            if (currentPoint != null) {
                double lat = currentPoint.getLatitude();
                double lng = currentPoint.getLongitude();
                String label = currentPoint.getName();

                // 'geo:' es el estándar para mostrar marcadores
                // El parámetro 'q' permite poner el pin y la etiqueta entre paréntesis
                String uriString = "geo:" + lat + "," + lng + "?q=" + lat + "," + lng + "(" + Uri.encode(label) + ")";
                Uri gmmIntentUri = Uri.parse(uriString);

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                // Verificamos si hay una app que pueda manejar el intent antes de lanzarlo
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    // Si el cel no tiene Google Maps, podemos abrirlo en el navegador como respaldo
                    Uri webUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + lat + "," + lng);
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
                    startActivity(webIntent);
                }
            } else {
                Toast.makeText(this, "Datos del lugar no disponibles", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btnCompartir.setOnClickListener(v -> {
            String nombreLugar = binding.txtTitulo.getText().toString();
            String descripcion = binding.txtDescripcion.getText().toString();
            String mensaje = nombreLugar + "\n" + descripcion;

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, mensaje);

            startActivity(Intent.createChooser(intent, "Compartir con"));
        });
    }
}