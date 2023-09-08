package com.banco.sucursal.controller.dto;

import lombok.Data;

@Data
public class TransaccionDTO {
    private int tipoTransaccion;
    private int idClienteOrigen;
    private int idProductoOrigen;
    private int idClienteDestino;
    private int idProductoDestino;
    private float monto;
}
