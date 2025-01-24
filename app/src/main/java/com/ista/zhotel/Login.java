package com.ista.zhotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText emailTextLogin, paswordTextLogin;
    Button botonLogin;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextLogin = findViewById(R.id.emailLogin);
        paswordTextLogin = findViewById(R.id.paswordLogin);
        botonLogin = findViewById(R.id.buttonLogin);
        textView2 = findViewById(R.id.textViewLo);

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registrar.class);
                startActivity(intent);
                finish();
            }
        });

        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = emailTextLogin.getText().toString().trim();
                String passwordUser = paswordTextLogin.getText().toString().trim();

                if (TextUtils.isEmpty(emailUser)) {
                    Toast.makeText(Login.this, "Ingrese el correo electrónico", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordUser)) {
                    Toast.makeText(Login.this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                getDatos(emailUser, passwordUser);
            }
        });
    }

    public void getDatos(String usuario, String contrasena) {
        String url = "http://192.168.0.106:8081/api/clientes/usuario/" + usuario;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if (response.length() > 0) {
                        JSONObject jsonObjectCliente = response.getJSONObject(0);
                        if (contrasena.equals(jsonObjectCliente.getString("contrasena"))) {
                            Intent intent = new Intent(getApplicationContext(), PantallaPrincipal.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Cliente no encontrado", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR DE CONEXIÓN", error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
