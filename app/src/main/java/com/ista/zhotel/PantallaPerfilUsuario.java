package com.ista.zhotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PantallaPerfilUsuario extends AppCompatActivity {
    private String cedula;
    private Long idCliente;
    private EditText txtTelefono,txtnombre,txtnombre2,txtapellido,txtapellido2,txtcontrasena;
    private Button btnEditar;
    private int edad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_perfil_usuario);
        getDatos(PantallaPrincipal.correoUsuario);
        Toast.makeText(getApplicationContext(), PantallaPrincipal.correoUsuario, Toast.LENGTH_LONG).show();
     actualizarDatos();
    }

    public void actualizarDatos(){
        btnEditar = findViewById(R.id.btnEditar);
        txtnombre = findViewById(R.id.txtNombre);
        txtnombre2 = findViewById(R.id.txtNombre2);
        txtapellido = findViewById(R.id.txtApellido);
        txtapellido2 = findViewById(R.id.txtApellido2);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtcontrasena = findViewById(R.id.pasword);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatosUsuario(txtcontrasena.getText().toString(),PantallaPrincipal.correoUsuario,idCliente);
                updateDatosPersona(txtnombre.getText().toString(),txtnombre2.getText().toString(),txtapellido.getText().toString(),txtapellido2.getText().toString(),txtTelefono.getText().toString(),edad,cedula);
                salir(v);
            }
        });
    }
    public void getDatos(String usuario){
        String url="http://192.168.18.230:8081/api/clientes/usuario/"+usuario;//endpoint.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length()>0) {
                        JSONObject jsonObjectCliente = response.getJSONObject(0);
                        cedula = jsonObjectCliente.getString("cedula_persona");
                        idCliente = jsonObjectCliente.getLong("idCliente");
                        String contrasena = jsonObjectCliente.getString("contrasena");
                        txtcontrasena = findViewById(R.id.pasword);
                        txtcontrasena.setText(contrasena);
                        getDatosByCedula(cedula);
                        Log.d("He", contrasena);
                        Log.d("He", cedula);
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

    //Obtener datos de persona
    public void getDatosByCedula(String cedula){
      //  String url="http://192.168.18.156:8081/api/personas/"+cedula;//endpoint.
        String url="http://192.168.18.230:8081/api/personas/"+cedula;//endpoint.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.length()>0) {
                        String nombre = response.getString("nombre");
                        String nombre2 = response.getString("nombre2");
                        String apellido = response.getString("apellido");
                        String apellido2 = response.getString("apellido2");
                        String telefono = response.getString("telefono");
                        edad = response.getInt("edad");
                        txtnombre = findViewById(R.id.txtNombre);
                        txtnombre2 = findViewById(R.id.txtNombre2);
                        txtapellido = findViewById(R.id.txtApellido);
                        txtapellido2 = findViewById(R.id.txtApellido2);
                        txtTelefono = findViewById(R.id.txtTelefono);
                        txtnombre.setText(nombre);
                        txtnombre2.setText(nombre2);
                        txtapellido.setText(apellido);
                        txtapellido2.setText(apellido2);
                        txtTelefono.setText(telefono);
                    }else{
                        Toast.makeText(getApplicationContext(), "Persona no encontrada", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException j) {
                    j.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON ERROR",error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    //ACTUALIZAR DATOS DE USUARIO
    public void updateDatosUsuario(String contrasena,String usuario,Long id){
        String url="http://192.168.18.230:8081/api/clientes/"+id;
        JSONObject requestBodyUsuario = new JSONObject();
        try{
            requestBodyUsuario.put("contrasena",contrasena);
            requestBodyUsuario.put("usuario",usuario);
        }catch(JSONException j){
            j.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequestUsuario = new JsonObjectRequest(Request.Method.PUT, url, requestBodyUsuario, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "ACTUALIZACIÓN CORRECTA", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequestUsuario);
        updatePassUsuario(contrasena);
    }

    //ACTUALIZAR DATOS DE PERSONA
    public void updateDatosPersona(String nom1,String nom2, String ape1, String ape2, String tel, int edad ,String cedula){
        String url="http://192.168.18.230:8081/api/personas/"+cedula;
        JSONObject requestBodyPersona = new JSONObject();
        try{
            requestBodyPersona.put("nombre",nom1);
            requestBodyPersona.put("nombre2",nom2);
            requestBodyPersona.put("apellido",ape1);
            requestBodyPersona.put("apellido2",ape2);
            requestBodyPersona.put("telefono",tel);
            requestBodyPersona.put("edad",edad);
        }catch(JSONException j){
            j.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequestPersona = new JsonObjectRequest(Request.Method.PUT, url, requestBodyPersona, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), "ACTUALIZACIÓN CORRECTA", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequestPersona);
    }
    //Actualizar contraseña en firebase
    public void updatePassUsuario(String contrasena){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String newPassword = contrasena;
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("He", "Contraseña cambiada en firebase");
                                //Toast.makeText(getApplicationContext(), "Contraseña actualizada con éxito", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Error al actualizar la contraseña", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void salir(View view){
        Intent inicio = new Intent(this, PantallaPrincipal.class);
        startActivity(inicio);
        finish();
    }

}