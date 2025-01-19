package com.ista.zhotel.model;

public interface Callback {
    void onEncabezadoObtenido(long idEncabezado);
    void onError(String errorMessage);
}
