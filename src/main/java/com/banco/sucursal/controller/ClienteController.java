package com.banco.sucursal.controller;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.logica.ClienteLogica;
import com.banco.sucursal.persistencia.Cliente;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    private ClienteLogica clienteLogica;

    public ClienteController(ClienteLogica clienteLogica) {
        this.clienteLogica = clienteLogica;
    }

    @PostMapping(path = "/cliente/agregar")
    public RespuestaDTO guardarCliente(@RequestBody ClienteDTO cliente) {
        clienteLogica.guardarCliente(cliente);
        return new RespuestaDTO("Cliente creado correctamente.");
    }

    @GetMapping(path = "cliente/obtener")
    public List<Cliente> obtenerClientes() {
        return clienteLogica.obtenerClientes();
    }

    @DeleteMapping(path = "cliente/eliminar/{idCliente}")
    public RespuestaDTO eliminarCliente(@PathVariable int idCliente) {
        if (clienteLogica.eliminarCliente(idCliente)) {
            return new RespuestaDTO("Eliminado correctamente.");
        } else {
            return new RespuestaDTO("No se encontro el cliente.");
        }
    }
}
