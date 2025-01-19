package com.ista.zhotel.model;

public class Persona {
    private String cedula_persona;
    private String nombre;
    private String nombre2;
    private String apellido;
    private String apellido2;
    private String telefono;
    private String direccion;
    private int edad;
    public Persona() {

    }
    public Persona(String cedula_persona, String nombre, String nombre2, String apellido, String apellido2, String telefono, String direccion, int edad) {
        this.cedula_persona = cedula_persona;
        this.nombre = nombre;
        this.nombre2 = nombre2;
        this.apellido = apellido;
        this.apellido2 = apellido2;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;
    }
    public String getCedula_persona() {
        return cedula_persona;
    }
    public void setCedula_persona(String cedula_persona) {
        this.cedula_persona = cedula_persona;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre2() {
        return nombre2;
    }
    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getApellido2() {
        return apellido2;
    }
    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
}
