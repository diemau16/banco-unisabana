package com.banco.sucursal.persistencia;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Producto {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProducto;
    @Column
    private int idCliente;
    @Column
    private int tipoProducto;
    @Column
    private float saldoProducto;
    @Column
    private boolean activo;
}