package com.ista.zhotel.model;

public interface EncabezadoCallBack {
    void onReservaObtenido(long idReserva);
    void onError(String errorMessage);
}
