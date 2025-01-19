package com.ista.zhotel.model;

import java.util.Date;

public class DetalleFactura {
    private Long idDetalleFac;

    private Double subTotal;
    private Long idEncabezado;

    public Long getIdDetalleFac() {
        return idDetalleFac;
    }

    public void setIdDetalleFac(Long idDetalleFac) {
        this.idDetalleFac = idDetalleFac;
    }


    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Long getIdEncabezado() {
        return idEncabezado;
    }

    public void setIdEncabezado(Long idEncabezado) {
        this.idEncabezado = idEncabezado;
    }
}
