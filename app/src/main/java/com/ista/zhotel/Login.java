package com.ista.zhotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText emailTextLogin, paswordTextLogin;
    Button botonLogin;
    TextView textView2;
    private FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(getApplicationContext(),PantallaPrincipal.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailTextLogin= findViewById(R.id.emailLogin);
        paswordTextLogin=findViewById(R.id.paswordLogin);
        mAuth = FirebaseAuth.getInstance();
        botonLogin= findViewById(R.id.buttonLogin);
        textView2 = findViewById(R.id.textViewLo);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Registrar.class);
                startActivity(intent);
                finish();
            }
        });
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser= emailTextLogin.getText().toString().trim();
                String passwordlUser= paswordTextLogin.getText().toString().trim();
                if (TextUtils.isEmpty(emailUser)){
                    Toast.makeText(Login.this, "Enten email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordlUser)){
                    Toast.makeText(Login.this, "Enter pasword", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(emailUser, passwordlUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            EditText auxUsuario=findViewById(R.id.emailLogin);
                            PantallaPrincipal.correoUsuario= auxUsuario.getText().toString();
                            Toast.makeText(getApplicationContext(), "Ingreso Exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(getApplicationContext(),PantallaPrincipal.class);
                            startActivity(intent);
                            finish();

                        } else {
                            EditText auxUsuario=findViewById(R.id.emailLogin);
                            PantallaPrincipal.correoUsuario= auxUsuario.getText().toString();
                            getDatos(emailUser.toString(),passwordlUser.toString());
                        }
                    }
                });
            }
        });
    }
    public void getDatos(String usuario, String contrasena){
        //String url="http://localhost:8081/api/clientes/usuario/"+usuario;//endpoint.
        String url = "http://192.168.18.230:8081/api/clientes/usuario/" + usuario;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length()>0) {
                        JSONObject jsonObjectCliente = response.getJSONObject(0);
                        if(contrasena.equals(jsonObjectCliente.getString("contrasena"))){
                            Intent intent= new Intent(getApplicationContext(),PantallaPrincipal.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Contraseña Incorrecta", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Cliente no encontrado", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR DE CONEXION",error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}