package com.ista.zhotel.model;

import java.util.Date;

public class EncabezadoFactura {
    private Long idEncabezado;
    private Long idCliente;
    private Long idReserva;

    private String fechaFactura;
    private Double total;

    public Long getIdEncabezado() {
        return idEncabezado;
    }

    public void setIdEncabezado(Long idEncabezado) {
        this.idEncabezado = idEncabezado;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long cedula_persona) {
        this.idCliente = cedula_persona;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
}
