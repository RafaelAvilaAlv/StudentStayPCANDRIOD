package com.ista.zhotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ista.zhotel.model.Habi;
import com.ista.zhotel.model.MyAdapterHabi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PantallaPrincipal extends AppCompatActivity {
    Button inicio;
    public  static String correoUsuario;
    RecyclerView recyclerViewhabi;
    MyAdapterHabi adapter;
    static ArrayList<Habi> habitacion = new ArrayList<>();
    Button Servicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        Button button1 = findViewById(R.id.button5);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button1, "scaleX", 1.3f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button1, "scaleY", 1.3f);
        scaleX.setDuration(2000);
        scaleY.setDuration(2000);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        scaleX.setInterpolator(new BounceInterpolator());
        scaleY.setInterpolator(new BounceInterpolator());
        scaleX.start();
        scaleY.start();
        Button button2 = findViewById(R.id.button2);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator translateX = ObjectAnimator.ofFloat(button2, "translationX", -30f, 30f);
                translateX.setDuration(200);
                translateX.setRepeatCount(3);
                translateX.setRepeatMode(ObjectAnimator.REVERSE);
                translateX.start();
                handler.postDelayed(this, 5000);
            }
        }, 5000);
        inicio= findViewById(R.id.button4);
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        Servicios= findViewById(R.id.button5);
        Servicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iniciar(view);
            }
        });
        recyclerViewhabi = findViewById(R.id.recyclerViewhabi);
        recyclerViewhabi.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapterHabi(this,habitacion);
        recyclerViewhabi.setAdapter(adapter);
        getDatos();
    }

    private void getDatos(){
        habitacion.clear();
        String url=Environment.BASE_URL+"/habitaciones";//endpoint.
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                pasarJson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Tu mensaje aquí", Toast.LENGTH_SHORT).show();Toast.makeText(PantallaPrincipal.this, "Error al obtener datos", Toast.LENGTH_SHORT).show();
            }
        }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void pasarJson( JSONArray array){
        for(int i=0;i<array.length();i++){
            JSONObject json=null;
            Habi habita= new Habi();
            try {
                json=array.getJSONObject(i);
                if (json.getString("estado").equals("Disponible")) {
                    habita.setIdHabitaciones((long) json.getInt("idHabitaciones"));//como viene del API
                    habita.setnHabitacion(json.getInt("nHabitacion"));
                    habita.setDescriphabi(json.getString("descriphabi"));
                    habita.setPrecio(json.getDouble("precio"));
                    String base64Image = json.getString("foto");
                    if (base64Image.startsWith("data:image/jpeg;base64,")) {
                        base64Image = base64Image.substring("data:image/jpeg;base64,".length());
                    } else if (base64Image.startsWith("data:image/jpg;base64,")) {
                        base64Image = base64Image.substring("data:image/jpg;base64,".length());
                    } else if (base64Image.startsWith("data:image/png;base64,")) {
                        base64Image = base64Image.substring("data:image/png;base64,".length());
                    }
                    habita.setFoto(base64Image);
                    habitacion.add(habita);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapter.notifyDataSetChanged();
    }
    public void Iniciar(View view){
        Intent servicio = new Intent(this, Servicio.class);
        startActivity(servicio);
    }

    public void Reservar(View view){
        Intent reservar = new Intent(this, PantallaReservar.class);
        startActivity(reservar);
    }

    public void Perfil(View view){
        Intent perfil = new Intent(this, PantallaPerfilUsuario.class);
        startActivity(perfil);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}