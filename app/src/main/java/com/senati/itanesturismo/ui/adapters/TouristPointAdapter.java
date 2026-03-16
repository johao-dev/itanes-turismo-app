package com.senati.itanesturismo.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.senati.itanesturismo.R;
import com.senati.itanesturismo.data.model.TouristPoint;
import com.bumptech.glide.Glide;
import com.senati.itanesturismo.databinding.ItemTouristPointBinding;

import java.util.List;

public class TouristPointAdapter extends RecyclerView.Adapter<TouristPointAdapter.ViewHolder>{

    private List<TouristPoint> lista;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(TouristPoint touristPoint);
    }

    public TouristPointAdapter(List<TouristPoint> lista, OnItemClickListener listener){
        this.lista = lista;
        this.listener = listener;
    }

    public void updateData(List<TouristPoint> nuevaLista) {
        this.lista = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTouristPointBinding binding = ItemTouristPointBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TouristPoint lugar = lista.get(position);

        holder.binding.txtNombreLugar.setText(lugar.getName());
        holder.binding.txtCategoriaLugar.setText(lugar.getCategory());

        Glide.with(holder.itemView.getContext())
                .load(lugar.getPhotoUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.binding.imgLugar);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(lugar);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista == null ? 0 : lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemTouristPointBinding binding;

        public ViewHolder(ItemTouristPointBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}