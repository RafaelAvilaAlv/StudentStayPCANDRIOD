package com.ista.zhotel;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.ista.zhotel.model.Callback;
import com.ista.zhotel.model.DetalleFactura;
import com.ista.zhotel.model.EncabezadoCallBack;
import com.ista.zhotel.model.EncabezadoFactura;
import com.ista.zhotel.model.Reservas;

import android.app.DatePickerDialog;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class PantallaReservar extends AppCompatActivity {
    private TextView txtFechaIn;
    private TextView txtFechaFin;
    private String selectedDateIn;
    private String selectedDateFin;
    private String descripHabi;
    private int numPiso;
    private int numHabi;
    private Long idPago;
    public  Long cedula;
    public  Long idReserva;
    public  Long idEncabezado;
    private TextView txtTotal;
    private TextView txtDias, txtprecio;
    public static double precio;
    public static int idHabicionRe;
    public static String correoUsuRe;
    public static Bitmap decodedByte1;
     private AutoCompleteTextView spnPersonas;
    ImageView adaptarImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_reservar);
        Button button = findViewById(R.id.button10);
        int colorStart = Color.parseColor("#056688");
        int colorEnd = Color.parseColor("#87CEEB");
        ObjectAnimator anim = ObjectAnimator.ofInt(button, "backgroundColor", colorStart, colorEnd);
        anim.setDuration(3000);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.start();

        TextView button1 = findViewById(R.id.txtTotal);
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

        ImageView imagen = (ImageView) findViewById(R.id.pgTarjeta);
        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TranslateAnimation animacion = new TranslateAnimation(0, 100, 0, 100);
                animacion.setDuration(1000); // duración en milisegundos
                animacion.setFillAfter(false); // Si quieres que la imagen se quede en la posición final
                imagen.startAnimation(animacion);
            }
        });

        spnPersonas = findViewById(R.id.nroPersonas);
        adaptarImagen= findViewById(R.id.imagenBase);
        adaptarImagen.setScaleType(ImageView.ScaleType.FIT_XY);
        adaptarImagen.setImageBitmap(decodedByte1);
        Integer[] datos={1,2,3,4};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnPersonas.setAdapter(adapter);
        calendar();
        txtprecio.setText(String.valueOf(precio));
        guardarReserva();
        getDatos(correoUsuRe);
        getHabitacion(idHabicionRe);
        asignarPago();
    }

    public void calendar(){
        txtFechaIn = findViewById(R.id.fechaInic);
        txtFechaFin = findViewById(R.id.fechafin);
        txtDias = findViewById(R.id.txtDias);
        txtprecio= findViewById(R.id.precioHabi);
        txtFechaIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerIn();
            }
        });
        txtFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerFin();
            }
        });
    }

    private void showDatePickerIn() {

        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        Calendar minFecha = Calendar.getInstance();
        minFecha.set(Calendar.YEAR, 2024);
        minFecha.set(Calendar.MONTH, Calendar.JANUARY);
        minFecha.set(Calendar.DAY_OF_MONTH, 1);
        constraintsBuilder.setStart(minFecha.getTimeInMillis());

        Calendar maxFecha = Calendar.getInstance();
        maxFecha.set(Calendar.YEAR, 2025);
        maxFecha.set(Calendar.MONTH, Calendar.APRIL);
        maxFecha.set(Calendar.DAY_OF_MONTH, 30);
        constraintsBuilder.setEnd(maxFecha.getTimeInMillis());

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Seleccione la fecha de inicio");
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
                selectedDateIn = dateFormat.format(calendar.getTime());
                txtFechaIn.setText(selectedDateIn);
            }
        });
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }
    

    private void showDatePickerFin() {
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder();
        Calendar minFecha = Calendar.getInstance();
        minFecha.set(Calendar.YEAR, 2024);
        minFecha.set(Calendar.MONTH, Calendar.JANUARY);
        minFecha.set(Calendar.DAY_OF_MONTH, 1);
        constraintsBuilder.setStart(minFecha.getTimeInMillis());
        Calendar maxFecha = Calendar.getInstance();
        maxFecha.set(Calendar.YEAR, 2025);
        maxFecha.set(Calendar.MONTH, Calendar.APRIL);
        maxFecha.set(Calendar.DAY_OF_MONTH, 30);
        constraintsBuilder.setEnd(maxFecha.getTimeInMillis());
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Seleccione la fecha final");
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
                selectedDateFin = dateFormat.format(calendar.getTime());
                txtFechaFin.setText(selectedDateFin);
                txtDias.setText(calcularDias());
                totalPrecio();
            }
        });

        datePicker.show(getSupportFragmentManager(), "DATE_PICKER_TAG");
    }

    public String calcularDias(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaIn = LocalDate.parse(selectedDateIn,format);
        LocalDate fechaFin = LocalDate.parse(selectedDateFin,format);
        int total = (int) fechaIn.until(fechaFin).getDays();
        return String.valueOf(total);
    }

    public void totalPrecio(){
        String inputText = txtDias.getText().toString().trim();
        txtTotal = findViewById(R.id.txtTotal);
        if (!inputText.isEmpty()) {
            try {
                double precioIni = Double.valueOf(inputText);
                double totalDo = precioIni * precio;
                txtTotal.setText(String.valueOf(totalDo));
            } catch (NumberFormatException e) {
                txtTotal.setText("0");
                e.printStackTrace();
            }
        } else {
            txtTotal.setText("0");
        }
    }
    public void getDatos(String usuario){
        String url="http://192.168.18.5:8081/api/clientes/usuario/"+usuario;//endpoint.
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    if(response.length()>0) {
                        JSONObject jsonObjectCliente = response.getJSONObject(0);
                        cedula = jsonObjectCliente.getLong("idCliente");
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
                if (error.getMessage() != null) {
                    Log.d("Error: Response", error.getMessage());
                } else {
                    Log.d("Error: Response", "El mensaje de error es null");
                }
            }
        });
        Volley.newRequestQueue(this).add(jsonArrayRequest);
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
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue.add(stringRequest);
    }

    public void guardarReserva() {
        Button guardar = findViewById(R.id.button10);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText diasRece = findViewById(R.id.txtDias);
                EditText fechaEntara = findViewById(R.id.fechaInic);
                EditText fechaFin = findViewById(R.id.fechafin);
                EditText personaRece = findViewById(R.id.nroPersonas);
                TextView totalRece = findViewById(R.id.txtTotal);
                String fechaEntradaString = fechaEntara.getText().toString();
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaEntrada = LocalDate.parse(fechaEntradaString, inputFormatter);
                // Obtener la fecha de salida y formatearla
                String fechaSalidaString = fechaFin.getText().toString();
                LocalDate fechaSalida = LocalDate.parse(fechaSalidaString, inputFormatter);
                // Convertir las fechas al formato "yyyy-MM-dd"
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.getDefault());
                String fechaEntradaFormatted = fechaEntrada.format(outputFormatter);
                String fechaSalidaFormatted = fechaSalida.format(outputFormatter);
                Reservas miReserva = new Reservas();
                miReserva.setIdHabitaciones((long) idHabicionRe);
                miReserva.setDias(Integer.valueOf(diasRece.getText().toString()));
                Log.d("TAG","Fechas;"+fechaEntradaFormatted+fechaSalidaFormatted);
                try {
                    miReserva.setFechaEntrada(fechaEntradaFormatted);
                    miReserva.setFechaSalida(fechaSalidaFormatted);
                    miReserva.setIdRecepcionista(null);
                    miReserva.setIdPago(idPago);
                    miReserva.setIdCliente(cedula);
                    miReserva.setEstado("Pendiente");
                    miReserva.setnPersona(Integer.valueOf(personaRece.getText().toString()));
                    miReserva.setTotal(Double.valueOf(totalRece.getText().toString()));
                    realizarSolicitudPOST("http://192.168.18.230:8081/api/reservas", miReserva);
                    crearEncabezad();
                    crearDetalle();
                    cambiarEstado(idHabicionRe);
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(PantallaReservar.this, R.style.MyAlertDialogTheme);
                    builder.setTitle("¡Reserva Solicitada!");
                    builder.setMessage("Tu solicitud de Reservacion ha sido procesada correctamente. Estaremos en contacto contigo pronto.");
                    builder.setIcon(android.R.drawable.ic_dialog_info);
                    builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Principal(null);
                        }
                    });

                    Vibrator va = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        va.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        va.vibrate(1000);
                    }
                    builder.show();
                } catch (DateTimeParseException | NumberFormatException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fecha o número de personas no válidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

public void buscarReser(final EncabezadoCallBack callBack){
    String url="http://192.168.18.230:8081/api/reservas";//endpoint.
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                if(response.length()>0) {
                    JSONObject jsonObjectCliente = response.getJSONObject(response.length() -1);
                    idReserva = jsonObjectCliente.getLong("idReserva");
                    callBack.onReservaObtenido(idReserva);
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
            Log.d("He",error.getMessage());
        }
    });
    Volley.newRequestQueue(this).add(jsonArrayRequest);

}

public void buscarEcabezado(final Callback callback){
    String url="http://192.168.18.230:8081/api/encabezadofactura";//endpoint.
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                if(response.length()>0) {
                    JSONObject jsonObjectCliente = response.getJSONObject(response.length() -1);
                    idEncabezado = jsonObjectCliente.getLong("idEncabezado");
                    callback.onEncabezadoObtenido(idEncabezado);
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
            Log.d("He",error.getMessage());
        }
    });
    Volley.newRequestQueue(this).add(jsonArrayRequest);
}

public void crearEncabezad(){
    buscarReser(new EncabezadoCallBack() {
        @Override
        public void onReservaObtenido(long idReserva) {
            Long rservas = idReserva ;
            Log.d("He", "id reservar en el crear:  " + rservas);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",Locale.getDefault());
            LocalDate fechaFin1 = LocalDate.now();
            String fechaFactura = fechaFin1.format(outputFormatter);
            TextView totalRece = findViewById(R.id.txtTotal);
            EncabezadoFactura encabezado= new EncabezadoFactura();
            getDatos(correoUsuRe);
            encabezado.setIdCliente(cedula);
            encabezado.setFechaFactura(fechaFactura);
            encabezado.setIdReserva(rservas);
            encabezado.setTotal(Double.valueOf(totalRece.getText().toString()));
            realizarSolicitudPOST("http://192.168.18.230:8081/api/encabezadofactura", encabezado);
        }

        @Override
        public void onError(String errorMessage) {

        }
    });

    }
    public void crearDetalle(){
        buscarEcabezado(new Callback() {
            @Override
            public void onEncabezadoObtenido(long idEncabezado) {
                Long restabel= idEncabezado;
                DetalleFactura detalle= new DetalleFactura();
                detalle.setIdEncabezado(restabel);
                detalle.setSubTotal(precio);
                realizarSolicitudPOST("http://192.168.18.230:8081/api/detallefactura", detalle);
            }
            @Override
            public void onError(String errorMessage) {
                Log.d("He", "callback  " + errorMessage);
            }
        });
    }

    public void Principal(View view){
        Intent princi = new Intent(this, PantallaPrincipal.class);
        startActivity(princi);
    }

    public void getHabitacion(int id){
        String url="http://192.168.18.230:8081/api/habitaciones/"+id;//endpoint.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.length()>0) {
                        descripHabi = response.getString("descriphabi");
                        numHabi = response.getInt("nHabitacion");
                        numPiso = response.getInt("nPiso");
                        Log.d("He","Descripcipn habi"+ descripHabi);
                        Log.d("He", "Numero habi"+numHabi);
                        Log.d("He", "Numero piso"+numPiso);
                    }else{
                        Toast.makeText(getApplicationContext(), "Habitacion no encontrada", Toast.LENGTH_LONG).show();
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

    public void cambiarEstado(int id){
            String url="http://192.168.18.230:8081/api/habitaciones/"+id;
            JSONObject requestBodyHabitacion = new JSONObject();
            try{
                requestBodyHabitacion.put("estado","Ocupado");
                requestBodyHabitacion.put("descriphabi",descripHabi);
                requestBodyHabitacion.put("foto",decodedByte1.toString());
                requestBodyHabitacion.put("nHabitacion",numHabi);
                requestBodyHabitacion.put("nPiso",numPiso);
                requestBodyHabitacion.put("precio",precio);
            }catch(JSONException j){
                j.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequestUsuario = new JsonObjectRequest(Request.Method.PUT, url, requestBodyHabitacion, new Response.Listener<JSONObject>() {
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
        }

    private void asignarPago(){
        final ImageView pagoTarjeta = findViewById(R.id.pgTarjeta);
        final ImageView pagoTransferencia = findViewById(R.id.pgtransfer);
        final ImageView pagoEfectivo = findViewById(R.id.pgEfectivo);

        pagoTarjeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPago=1l;
                pagoTarjeta.setBackgroundResource(R.drawable.border_red);
                pagoTransferencia.setBackground(null);
                pagoEfectivo.setBackground(null);
            }
        });
        pagoTransferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPago=2l;
                pagoTransferencia.setBackgroundResource(R.drawable.border_red);
                pagoTarjeta.setBackground(null);
                pagoEfectivo.setBackground(null);
            }
        });
        pagoEfectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idPago=3l;
                pagoEfectivo.setBackgroundResource(R.drawable.border_red);
                pagoTarjeta.setBackground(null);
                pagoTransferencia.setBackground(null);
            }
        });
    }
}
