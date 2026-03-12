package com.senati.itanesturismo.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.senati.itanesturismo.R;
import com.senati.itanesturismo.data.model.TouristPoint;
import com.bumptech.glide.Glide;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder>{

    List<TouristPoint> lista;

    public FavoriteAdapter(List<TouristPoint> lista){
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombre;
        ImageView imagen;

        public ViewHolder(View itemView){
            super(itemView);

            nombre = itemView.findViewById(R.id.txtNombreLugar);
            imagen = itemView.findViewById(R.id.imgLugar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tourist_point,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        TouristPoint lugar = lista.get(position);

        holder.nombre.setText(lugar.getName());

        Glide.with(holder.itemView.getContext())
                .load(lugar.getPhotoUrl())
                .into(holder.imagen);
    }

    @Override
    public int getItemCount(){
        return lista.size();
    }
}