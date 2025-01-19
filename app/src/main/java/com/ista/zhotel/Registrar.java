package com.ista.zhotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.ista.zhotel.model.Persona;
import com.ista.zhotel.model.cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class Registrar extends AppCompatActivity {
    TextInputEditText emailText, paswordText,auxcedula,auxnombre,auxnombre1,auxapelldio,auxapellido2,auxtelefono;
    Button boton1;
    TextView textView1;
    private FirebaseAuth mAuth;
    private TextView birthDateTextView;
    private String selectedDate;
    public void onStart () {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUser.reload();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        emailText= findViewById(R.id.email);
        paswordText=findViewById(R.id.pasword);
        auxcedula = findViewById(R.id.cedula);
        auxnombre = findViewById(R.id.Nombre);
        auxnombre1 = findViewById(R.id.Nombre1);
        auxapelldio = findViewById(R.id.Apellido);
        auxapellido2 = findViewById(R.id.Apellido1);
        auxtelefono = findViewById(R.id.telefono);
        mAuth = FirebaseAuth.getInstance();
        boton1= findViewById(R.id.button);
        textView1 = findViewById(R.id.textViewRe);
        calendar();
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser= emailText.getText().toString().trim();
                String passwordlUser= paswordText.getText().toString().trim();
                String cedula= auxcedula.getText().toString().trim();
                String nom1= auxnombre.getText().toString().trim();
                String nom2= auxnombre1.getText().toString().trim();
                String ape1= auxapelldio.getText().toString().trim();
                String ape2= auxapellido2.getText().toString().trim();
                String cel= auxtelefono.getText().toString().trim();
                if (TextUtils.isEmpty(emailUser)){
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(passwordlUser)){
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cedula)|| cedula.length() != 10){
                    Toast.makeText(getApplicationContext(), "Por favor, complete su cedula de manera correcta", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nom1)){
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!nom1.matches("[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese el primer nombre válido con solo letras", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nom2)){
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!nom2.matches("[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese el segundo nombre válido con solo letras", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ape1)){
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!ape1.matches("[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese el apellido paterno  válido con solo letras", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ape2)){
                    Toast.makeText(getApplicationContext(), "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!ape2.matches("[a-zA-Z]+")) {
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese el apellido materno  válido con solo letras", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(cel)|| cel.length() != 10){
                    Toast.makeText(getApplicationContext(), "Por favor, ingrese un numero de telefono valido", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(emailUser, passwordlUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()){
                                guardarPersona();
                                //guardarClietnes();
                                EditText auxUsuario=findViewById(R.id.email);
                                PantallaPrincipal.correoUsuario= auxUsuario.getText().toString();
                                Toast.makeText(Registrar.this, "Authentication Exit.", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(getApplicationContext(),Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(Registrar.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                        }
                    }
                });
            }
        });
    }
    public void calendar(){
            birthDateTextView = findViewById(R.id.birthDateTextView);
            birthDateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker();
                }
            });
        }

    private void showDatePicker() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();

        Calendar maxFecha = Calendar.getInstance();
        maxFecha.set(Calendar.YEAR, 2024);
        maxFecha.set(Calendar.MONTH, Calendar.DECEMBER);
        maxFecha.set(Calendar.DAY_OF_MONTH, 31);
        constraintsBuilder.setEnd(maxFecha.getTimeInMillis());
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Selecciona tu fecha de nacimiento");
        builder.setCalendarConstraints(constraintsBuilder.build());
        MaterialDatePicker<Long> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getDefault());
                calendar.setTimeInMillis(selection);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                selectedDate = dateFormat.format(calendar.getTime());
                birthDateTextView.setText(selectedDate);
            }
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }
    private void   guardarPersona(){
        EditText auxcedula=findViewById(R.id.cedula);
        EditText auxnombre=findViewById(R.id.Nombre);
        EditText auxnombre1=findViewById(R.id.Nombre1);
        EditText auxapelldio=findViewById(R.id.Apellido);
        EditText auxapellido2=findViewById(R.id.Apellido1);
        EditText auxtelefono=findViewById(R.id.telefono);
        Persona persona = new Persona();
        persona.setCedula_persona(auxcedula.getText().toString());
        persona.setNombre(auxnombre.getText().toString());
        persona.setNombre2(auxnombre1.getText().toString());
        persona.setApellido(auxapelldio.getText().toString());
        persona.setApellido2(auxapellido2.getText().toString());
        persona.setTelefono(auxtelefono.getText().toString());
        persona.setEdad(calcularEdad());
        realizarSolicitudPOST("http://192.168.18.230:8081/api/personas",persona);

        guardarClietnes();
    }
    public void guardarClietnes() {
        EditText auxcorreo = findViewById(R.id.email);
        EditText auxContraseña = findViewById(R.id.pasword);
        EditText auxcedula = findViewById(R.id.cedula);
        cliente clienteNuevo= new cliente();
        clienteNuevo.setContrasena(auxContraseña.getText().toString());
        clienteNuevo.setUsuario(auxcorreo.getText().toString());
        clienteNuevo.setCedula_persona(auxcedula.getText().toString());
        realizarSolicitudPOST("http://192.168.18.230:8081/api/clientes",clienteNuevo);
    }
    private <T>void realizarSolicitudPOST(String url,  final T objeto) {
        RequestQueue queue = Volley.newRequestQueue(this);
        Gson gson = new Gson();
        final String personaJson = gson.toJson(objeto);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "Error en la solicitud: " + error.toString());
                    }
                }) {
            @Override
            public byte[] getBody() {
                return personaJson.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        queue.add(stringRequest);
    }
    public int calcularEdad(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaIn = LocalDate.parse(selectedDate,format);
        LocalDate fechaFin = LocalDate.now();
        int total = (int) fechaIn.until(fechaFin).getYears();
        return total;
    }
}