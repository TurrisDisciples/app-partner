package com.argentruck.argentruck_partner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.util.List;

import static com.argentruck.argentruck_partner.R.id.fecha;

public class TripsAdapter extends ArrayAdapter<Trip> {
    public TripsAdapter(Context context, List<Trip> objects) {
        super(context, 0, objects);
    }

    private char contInicial = 'A';

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.trips_item_list,
                    parent,
                    false);

            holder = new ViewHolder();
            holder.inicial = (ImageView) convertView.findViewById(R.id.image_view);
            holder.viaje = (TextView) convertView.findViewById(R.id.viaje);
            holder.capacidad = (TextView) convertView.findViewById(R.id.capacidad);
            holder.fecha = (TextView) convertView.findViewById(fecha);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Client actual.
        Trip viaje = getItem(position);

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(String.valueOf(contInicial), Color.BLUE);
        contInicial++;

        holder.inicial.setImageDrawable(drawable);
        holder.viaje.setText(viaje.getOrigen() + " - " + viaje.getDestino());
        holder.capacidad.setText(viaje.getCapacidad());
        holder.fecha.setText(viaje.getFecha());

        return convertView;
    }

    static class ViewHolder {
        ImageView inicial;
        TextView viaje;
        TextView capacidad;
        TextView fecha;
    }
}
