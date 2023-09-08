package com.banco.sucursal.persistencia;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Transaccion {
    @Id
    @Column
    private String idTransaccion;
    @Column
    private LocalDateTime horaTransaccion;
    @Column
    private int tipoTransaccion;
    @Column
    private String idClienteOrigen;
    @Column
    private String idProductoOrigen;
    @Column
    private String idClienteDestino;
    @Column
    private String idProductoDestino;
    @Column
    private float monto;
}
