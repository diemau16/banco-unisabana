package com.banco.sucursal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class Controller {
    private ClienteRepository estudianteRepository;

    public Controller(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    @PostMapping(path = "/Banco/CrearCliente")
    public String crearCliente(@RequestBody Cliente cliente) {
        clienteRepository.save(cliente);
        return "Cliente Creado Correctamente";
    }
}
