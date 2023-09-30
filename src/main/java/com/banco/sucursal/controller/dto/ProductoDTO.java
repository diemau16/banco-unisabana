package com.banco.sucursal.controller.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private int idCliente;
    private int tipoProducto;

    public ProductoDTO(int idCliente, int tipoProducto) {
        this.idCliente = idCliente;
        this.tipoProducto = tipoProducto;
    }
}