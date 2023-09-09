package com.banco.sucursal.logica;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.persistencia.Cliente;
import com.banco.sucursal.persistencia.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ClienteLogica {
    private ClienteRepository clienteRepository;

    public ClienteLogica(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void guardarCliente(ClienteDTO clienteDTO) {
        Cliente clienteBD = new Cliente();
        clienteBD.setActivo(true);
        clienteBD.setNombres(clienteDTO.getNombres());
        clienteBD.setApellidos(clienteDTO.getApellidos());
        clienteBD.setEdad(clienteDTO.getEdad());
        clienteRepository.save(clienteBD);
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerClientePorId(int idCliente) {
        List<Cliente> listaClientes = obtenerClientes();
        for (Cliente cliente : listaClientes) {
            if (idCliente == cliente.getIdCliente()) {
                return cliente;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente.");
    }

    public void desactivarCliente(int idCliente) {
        List<Cliente> listaClientes = obtenerClientes();
        for (Cliente cliente : listaClientes) {
            if (idCliente == cliente.getIdCliente()) {
                cliente.setActivo(false);
                clienteRepository.save(cliente);
                return;
            }
        }
        throw new IllegalArgumentException("No existe el cliente.");
    }

    public void activarCliente(int idCliente) {
        List<Cliente> listaClientes = obtenerClientes();
        for (Cliente cliente : listaClientes) {
            if (idCliente == cliente.getIdCliente()) {
                cliente.setActivo(true);
                clienteRepository.save(cliente);
                return;
            }
        }
        throw new IllegalArgumentException("No existe el cliente.");
    }
}
