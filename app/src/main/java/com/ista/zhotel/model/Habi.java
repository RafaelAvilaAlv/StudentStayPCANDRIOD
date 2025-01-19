package com.ista.zhotel.model;

public class Habi {
    private Long idHabitaciones;
    private Double precio;
    private int nHabitacion;
    private int nPiso;
    private int idCategoria;
    private String descriphabi;
    private String foto;
    private String estado;

    private String numeroHabitacion;

    public Long getIdHabitaciones() {
        return idHabitaciones;
    }

    @Override
    public String toString() {
        return String.valueOf(getnHabitacion());
    }

    public void setIdHabitaciones(Long idHabitaciones) {
        this.idHabitaciones = idHabitaciones;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getnHabitacion() {
        return nHabitacion;
    }

    public void setnHabitacion(int nHabitacion) {
        this.nHabitacion = nHabitacion;
    }

    public int getnPiso() {
        return nPiso;
    }

    public void setnPiso(int nPiso) {
        this.nPiso = nPiso;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescriphabi() {
        return descriphabi;
    }

    public void setDescriphabi(String descriphabi) {
        this.descriphabi = descriphabi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}


