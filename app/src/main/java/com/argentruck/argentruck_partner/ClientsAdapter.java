package com.argentruck.argentruck_partner;

import android.widget.ImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ClientsAdapter extends ArrayAdapter<Client> {
    public ClientsAdapter(Context context, List<Client> objects) {
        super(context, 0, objects);
    }

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
                    R.layout.clients_item_list,
                    parent,
                    false);

            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.telefono = (TextView) convertView.findViewById(R.id.telefono);
            holder.capacidadContratada = (TextView) convertView.findViewById(R.id.capacidad);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Client actual.
        Client cliente = getItem(position);

        // Setup.
        holder.name.setText(cliente.getNombreYApellido());
        holder.telefono.setText(cliente.getTelefono());
        holder.capacidadContratada.setText(cliente.getCapacidadContratada());
        Glide.with(getContext()).load(cliente.getImagen()).into(holder.avatar);

        return convertView;
    }

    static class ViewHolder {
        ImageView avatar;
        TextView name;
        TextView telefono;
        TextView capacidadContratada;
    }
}
