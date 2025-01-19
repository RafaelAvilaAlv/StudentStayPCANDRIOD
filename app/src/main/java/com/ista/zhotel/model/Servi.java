package com.ista.zhotel.model;

public class Servi {
    private long idTipo_servicio;
    private String titulo;
    private String descripcion;
    private String foto;

    public long getIdTipo_servicio() {
        return idTipo_servicio;
    }
    public void setIdTipo_servicio(long id) {
        this.idTipo_servicio = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
