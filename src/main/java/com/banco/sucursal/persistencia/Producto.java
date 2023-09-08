package com.banco.sucursal.persistencia;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Producto {
    @Id
    @Column
    private int idProducto;
    @Column
    private int idCliente;
    @Column
    private int tipoProducto;
    @Column
    private float saldoProducto;
}
