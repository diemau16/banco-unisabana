package com.banco.sucursal.persistencia;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table
@Data
public class Transaccion {
    @Id
    @Column
    private int idTransaccion;
    @Column
    private LocalDate horaTransaccion;
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
