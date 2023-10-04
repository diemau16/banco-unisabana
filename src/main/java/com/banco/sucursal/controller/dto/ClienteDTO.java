package com.banco.sucursal.controller.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    private String nombres;
    private String apellidos;
    private int edad;

    public ClienteDTO(String nombres, String apellidos, int edad) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public ClienteDTO() {
    }
}