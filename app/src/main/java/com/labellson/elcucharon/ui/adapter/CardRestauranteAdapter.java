package com.labellson.elcucharon.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Restaurante;

import java.util.List;

/**
 * Created by dani on 26/05/15.
 */
public class CardRestauranteAdapter extends RecyclerView.Adapter<CardRestauranteAdapter.ViewHolder> {

    private List<Restaurante> restaurantes;

    public CardRestauranteAdapter(List<Restaurante> restaurantes) {
        this.restaurantes = restaurantes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurante_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindRestaurante(restaurantes.get(position));
    }

    @Override
    public int getItemCount() {
        return restaurantes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombre;
        private ImageView background;

        public ViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.label_nombre_cardview);
            background = (ImageView) itemView.findViewById(R.id.card_restaurante_background);
        }

        public void bindRestaurante(Restaurante r){
            nombre.setText(r.getNombre());
            background.setImageBitmap(r.getFoto());
        }
    }
}
