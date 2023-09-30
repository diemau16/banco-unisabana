package com.banco.sucursal.unit.logica;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.logica.ClienteLogica;
import com.banco.sucursal.persistencia.Cliente;
import com.banco.sucursal.persistencia.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteLogicaTest {

    @InjectMocks
    ClienteLogica logica;

    @Mock
    ClienteRepository clienteRepository;

    @Test
    void Cuando_ingreseDatosCliente_Entonces_guardarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("Joseph", "Mantilla", 20);

        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente clienteGuardado = invocation.getArgument(0);
            clienteGuardado.setIdCliente(1);
            return clienteGuardado;
        });

        logica.guardarCliente(clienteDTO);

        verify(clienteRepository, times(1)).save(any(Cliente.class));

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);
        verify(clienteRepository).save(clienteCaptor.capture());

        Cliente clienteGuardado = clienteCaptor.getValue();
        assertEquals(1, clienteGuardado.getIdCliente());
        assertEquals("Joseph", clienteGuardado.getNombres());
        assertEquals("Mantilla", clienteGuardado.getApellidos());
        assertEquals(20, clienteGuardado.getEdad());
    }


    @Test
    void Dado_cliente_Cuando_busqueCliente_Entonces_existe() {
        int idClienteExistente = 1;
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setActivo(true);
        cliente.setNombres("Joseph");
        cliente.setApellidos("Mantilla");
        cliente.setEdad(20);
        listaClientes.add(cliente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        boolean existe = logica.existeCliente(idClienteExistente);

        assertTrue(existe);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void Dado_cliente_Cuando_busqueClienteInexistente_Entonces_noExiste() {
        int idClienteExistente = 2;
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setActivo(true);
        cliente.setNombres("Joseph");
        cliente.setApellidos("Mantilla");
        cliente.setEdad(20);
        listaClientes.add(cliente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        boolean existe = logica.existeCliente(idClienteExistente);

        assertFalse(existe);
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void Dado_listaClientes_Cuando_obtenerClientes_Entonces_listarClientes() {
        // Arrange
        List<Cliente> listaClientes = new ArrayList<>();
        Cliente primerCliente = new Cliente();
        primerCliente.setIdCliente(1);
        primerCliente.setActivo(true);
        primerCliente.setNombres("Joseph");
        primerCliente.setApellidos("Mantilla");
        primerCliente.setEdad(20);
        listaClientes.add(primerCliente);

        Cliente segundoCliente = new Cliente();
        segundoCliente.setIdCliente(2);
        segundoCliente.setActivo(true);
        segundoCliente.setNombres("Diego");
        segundoCliente.setApellidos("Acosta");
        segundoCliente.setEdad(19);
        listaClientes.add(segundoCliente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        List<Cliente> clientesObtenidos = logica.obtenerClientes();

        assertNotNull(clientesObtenidos);
        assertEquals(2, clientesObtenidos.size());

        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void Dado_clienteExistente_Cuando_compruebeExistencia_Entonces_retornarCliente() {
        int idClienteExistente = 1;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setIdCliente(idClienteExistente);
        clienteExistente.setNombres("Joseph");
        clienteExistente.setApellidos("Mantilla");
        clienteExistente.setEdad(20);

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(clienteExistente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        Cliente encontrado = logica.encontrarCliente(idClienteExistente);

        assertNotNull(encontrado);
        assertEquals(idClienteExistente, encontrado.getIdCliente());
    }

    @Test
    void Dado_clienteInexistente_Cuando_compruebeExistencia_Entonces_lanzarExcepcion() {
        int idClienteInexistente = 1;
        List<Cliente> listaClientes = new ArrayList<>();

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        assertThrows(ResponseStatusException.class, () -> {
            logica.encontrarCliente(idClienteInexistente);
        });
    }

    @Test
    void Dado_idClienteExistente_Cuando_compruebeExistencia_Entonces_retornarCliente() {
        int idClienteExistente = 1;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setIdCliente(idClienteExistente);
        clienteExistente.setNombres("Joseph");
        clienteExistente.setApellidos("Mantilla");
        clienteExistente.setEdad(20);

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(clienteExistente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        Cliente encontrado = logica.obtenerClientePorId(idClienteExistente);

        assertNotNull(encontrado);
        assertEquals(idClienteExistente, encontrado.getIdCliente());
    }

    @Test
    void Dado_idClienteInexistente_Cuando_compruebeExistencia_Entonces_lanzarExcepcion() {
        int idClienteInexistente = 1;

        List<Cliente> listaClientes = new ArrayList<>();

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        assertThrows(ResponseStatusException.class, () -> {
            logica.obtenerClientePorId(idClienteInexistente);
        });
    }

    @Test
    void Dado_idClienteExistente_Cuando_desactivarCliente_Entonces_clienteDesactivado() {
        int idClienteExistente = 1;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setIdCliente(idClienteExistente);
        clienteExistente.setNombres("Joseph");
        clienteExistente.setApellidos("Mantilla");
        clienteExistente.setEdad(20);

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(clienteExistente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        logica.desactivarCliente(idClienteExistente);

        assertFalse(clienteExistente.isActivo());
        verify(clienteRepository, times(1)).save(clienteExistente);
    }

    @Test
    void Dado_idClienteInexistente_Cuando_desactivarCliente_Entonces_lanzarExcepcion() {
        int idClienteInexistente = 2;

        List<Cliente> listaClientes = new ArrayList<>();

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        assertThrows(ResponseStatusException.class, () -> {
            logica.desactivarCliente(idClienteInexistente);
        });
    }

    @Test
    void Dado_idClienteExistente_Cuando_activarCliente_Entonces_clienteActivado() {
        int idClienteExistente = 1;
        Cliente clienteExistente = new Cliente();
        clienteExistente.setIdCliente(idClienteExistente);
        clienteExistente.setNombres("Joseph");
        clienteExistente.setApellidos("Mantilla");
        clienteExistente.setEdad(20);

        List<Cliente> listaClientes = new ArrayList<>();
        listaClientes.add(clienteExistente);

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        logica.activarCliente(idClienteExistente);

        assertTrue(clienteExistente.isActivo());
        verify(clienteRepository, times(1)).save(clienteExistente);
    }

    @Test
    void Dado_idClienteInexistente_Cuando_activarCliente_Entonces_lanzarExcepcion() {
        int idClienteInexistente = 2;

        List<Cliente> listaClientes = new ArrayList<>();

        when(clienteRepository.findAll()).thenReturn(listaClientes);

        assertThrows(ResponseStatusException.class, () -> {
            logica.activarCliente(idClienteInexistente);
        });
    }
}