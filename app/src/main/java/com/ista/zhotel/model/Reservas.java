package com.ista.zhotel.model;

public class Reservas {
    private Long idReserva;
    private Long idPago;
    private String fechaSalida;
    private String fechaEntrada;
    private Double total;
    private Long idHabitaciones;
    private Long idRecepcionista;
    private Long idCliente;
    private Integer dias;
    private Integer nPersona;
    private String estado;

    public Long getIdReserva() {
        return idReserva;
    }
    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
    public Long getIdPago() {
        return idPago;
    }
    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }
    public String getFechaSalida() {
        return fechaSalida;
    }
    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    public String getFechaEntrada() {
        return fechaEntrada;
    }
    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }
    public Double getTotal() {
        return total;
    }
    public void setTotal(Double total) {
        this.total = total;
    }
    public Long getIdHabitaciones() {
        return idHabitaciones;
    }
    public void setIdHabitaciones(Long idHabitaciones) {
        this.idHabitaciones = idHabitaciones;
    }
    public Long getIdRecepcionista() {
        return idRecepcionista;
    }
    public void setIdRecepcionista(Long idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }
    public Long getIdCliente() {
        return idCliente;
    }
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }
    public Integer getDias() {
        return dias;
    }
    public void setDias(Integer dias) {
        this.dias = dias;
    }
    public Integer getnPersona() {
        return nPersona;
    }
    public void setnPersona(Integer nPersona) {
        this.nPersona = nPersona;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
}
