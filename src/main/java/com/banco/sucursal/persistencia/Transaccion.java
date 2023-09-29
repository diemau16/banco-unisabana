package com.banco.sucursal.persistencia;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Transaccion {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTransaccion;
    @Column
    private LocalDateTime horaTransaccion;
    @Column
    private int tipoTransaccion;
    @Column
    private int idClienteOrigen;
    @Column
    private int idProductoOrigen;
    @Column
    private int idClienteDestino;
    @Column
    private int idProductoDestino;
    @Column
    private float monto;
}