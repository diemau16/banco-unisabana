package com.banco.sucursal.persistencia;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Cliente {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;
    @Column
    private boolean activo;
    @Column
    private String nombres;
    @Column
    private String apellidos;
    @Column
    private int edad;
}