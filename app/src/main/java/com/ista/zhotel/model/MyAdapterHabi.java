package com.ista.zhotel.model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ista.zhotel.PantallaPrincipal;
import com.ista.zhotel.PantallaReservar;
import com.ista.zhotel.R;

import java.util.ArrayList;

public class MyAdapterHabi extends RecyclerView.Adapter<MyAdapterHabi.MyViewHolder> {
    Context context;
    ArrayList<Habi> habis;
    public MyAdapterHabi(Context c, ArrayList<Habi> p) {
        context = c;
        habis = p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview_layout, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int numeroHabitacion = habis.get(position).getnHabitacion();
        holder.titulo.setText(String.valueOf(numeroHabitacion));
        holder.descripcion.setText(habis.get(position).getDescriphabi());
        String base64Image = habis.get(position).getFoto();
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imagen.setImageBitmap(decodedByte);
        final int finalPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PantallaReservar.class);
                int idHabitaciones= Math.toIntExact(habis.get(finalPosition).getIdHabitaciones());
                PantallaReservar.precio=habis.get(finalPosition).getPrecio();
                PantallaReservar.decodedByte1= decodedByte;
                PantallaReservar.idHabicionRe=idHabitaciones;
                PantallaReservar.correoUsuRe= PantallaPrincipal.correoUsuario;
                intent.putExtra("EXTRA_ID_HABITACION", habis.get(finalPosition).getIdHabitaciones());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return habis.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, descripcion;
        ImageView imagen;
        public MyViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
            imagen = itemView.findViewById(R.id.imagen);
        }
    }
}
