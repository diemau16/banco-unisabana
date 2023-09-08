package com.banco.sucursal.persistencia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Cliente {
    @Id
    @Column
    private String idCliente;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private int edad;
    @Column
    private float saldoCliente;
}
