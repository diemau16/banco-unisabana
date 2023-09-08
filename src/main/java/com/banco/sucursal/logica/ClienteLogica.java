package com.banco.sucursal.logica;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.persistencia.Cliente;
import com.banco.sucursal.persistencia.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteLogica {
    private ClienteRepository clienteRepository;

    public ClienteLogica(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public void guardarCliente(ClienteDTO clienteDTO) {
        Cliente clienteBD = new Cliente();
        clienteBD.setNombres(clienteDTO.getNombres());
        clienteBD.setApellidos(clienteDTO.getApellidos());
        clienteBD.setEdad(clienteDTO.getEdad());
        clienteBD.setSaldoCliente(0);
        clienteRepository.save(clienteBD);
    }

    public List<Cliente> obtenerClientes() {
        return clienteRepository.findAll();
    }

    public boolean eliminarCliente(int idCliente) {
        List<Cliente> listaClientes = obtenerClientes();
        for (Cliente cliente : listaClientes) {
            if (cliente.getIdCliente() == idCliente) {
                clienteRepository.delete(cliente);
                return true;
            }
        }
        return false;
    }
}
