package com.banco.sucursal.controller.dto;

import lombok.Data;

@Data
public class TransaccionDTO {
    private int tipoTransaccion;
    private int idClienteOrigen;
    private int idProductoOrigen;
    private int idProductoDestino;
    private float monto;

    public TransaccionDTO(int tipoTransaccion, int idClienteOrigen, int idProductoOrigen, int idProductoDestino, float monto) {
        this.tipoTransaccion = tipoTransaccion;
        this.idClienteOrigen = idClienteOrigen;
        this.idProductoOrigen = idProductoOrigen;
        this.idProductoDestino = idProductoDestino;
        this.monto = monto;
    }
}