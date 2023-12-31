package com.banco.sucursal.controller;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.logica.ClienteLogica;
import com.banco.sucursal.persistencia.Cliente;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/cliente/obtener")
    public List<Cliente> obtenerClientes() {
        return clienteLogica.obtenerClientes();
    }

    @GetMapping(path = "/cliente/obtener/{idCliente}")
    public Cliente obtenerClientePorId(@PathVariable int idCliente) {
        return clienteLogica.obtenerClientePorId(idCliente);
    }

    @PutMapping(path = "/cliente/activar/{idCliente}")
    public ResponseEntity<RespuestaDTO> activarCliente(@PathVariable int idCliente) {
        try {
            clienteLogica.activarCliente(idCliente);
            return ResponseEntity.ok(new RespuestaDTO("Activado correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO("Cliente no se pudo activar: " + e.getMessage()));
        }
    }

    @PutMapping(path = "/cliente/desactivar/{idCliente}")
    public ResponseEntity<RespuestaDTO> desactivarCliente(@PathVariable int idCliente) {
        try {
            clienteLogica.desactivarCliente(idCliente);
            return ResponseEntity.ok(new RespuestaDTO("Desactivado correctamente."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new RespuestaDTO("Cliente no se pudo desactivar: " + e.getMessage()));
        }
    }
}
