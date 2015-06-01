package com.labellson.elcucharon.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Reserva;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by dani on 31/05/15.
 */
public class ListReservaAdapter extends RecyclerView.Adapter<ListReservaAdapter.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private List<Reserva> reservas;
    private View.OnClickListener listener;
    private View.OnLongClickListener longListener;

    public ListReservaAdapter(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserva_view, parent, false);

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binReserva(reservas.get(position));
    }

    @Override
    public int getItemCount() {
        return reservas.size();
    }

    @Override
    public void onClick(View v) {
        if(listener != null) listener.onClick(v);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        if(longListener != null) longListener.onLongClick(v);
        return true;
    }

    public void setOnLongClickListener(View.OnLongClickListener listener){
        longListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumb;
        private TextView nombre_restaurante;
        private TextView fecha_reserva;
        private TextView hora_reserva;
        private final SimpleDateFormat dfHora = new SimpleDateFormat("HH:mm");

        public ViewHolder(View itemView){
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.img_restaurante);
            nombre_restaurante = (TextView) itemView.findViewById(R.id.txt_restaurante_nombre);
            fecha_reserva = (TextView) itemView.findViewById(R.id.txt_reserva_fecha);
            hora_reserva = (TextView) itemView.findViewById(R.id.txt_reserva_hora);
        }

        public void binReserva(Reserva r){
            thumb.setImageBitmap(r.getRestaurante().getFoto());
            nombre_restaurante.setText(r.getRestaurante().getNombre());
            fecha_reserva.setText(new SimpleDateFormat("dd").format(r.getFecha())+" de "+ new SimpleDateFormat("MMMM").format(r.getFecha())+" de "+ new SimpleDateFormat("yyyy").format(r.getFecha()));
            hora_reserva.setText("A las " + dfHora.format(r.getFecha()));
        }
    }
}
