package com.banco.sucursal.integration.controller;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.persistencia.Cliente;
import com.banco.sucursal.persistencia.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ClienteControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void alAgregarCliente_entoncesDevuelveRespuestaExitosaYGuardaCliente() {
        // Given
        ClienteDTO clienteDTO = new ClienteDTO("Carlos", "Gomez", 43);
        // When
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.postForEntity("/cliente/agregar", clienteDTO, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Cliente creado correctamente.", responseEntity.getBody().getRespuesta());
        Cliente clienteGuardado = clienteRepository.findById(1).orElse(null);
        assertEquals("Carlos", clienteGuardado.getNombres());
        assertEquals("Gomez", clienteGuardado.getApellidos());
        assertEquals(43, clienteGuardado.getEdad());
    }

    @Test
    void dadoClientes_alObtenerClientes_entoncesDevuelveListaEstudiantes() {
        // Given
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente1 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Juan");
        cliente1.setApellidos("Garcia");
        cliente1.setEdad(20);
        clientes.add(cliente1);
        Cliente cliente2 = new Cliente();
        cliente2.setActivo(true);
        cliente2.setNombres("Luis");
        cliente2.setApellidos("Rodriguez");
        cliente2.setEdad(20);
        clientes.add(cliente2);
        clienteRepository.saveAll(clientes);
        // When
        ResponseEntity<Cliente[]> responseEntity = restTemplate.getForEntity("/cliente/obtener", Cliente[].class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Cliente[] clientesResponse = responseEntity.getBody();
        assert clientesResponse != null;
        assertEquals(2, clientesResponse.length);
        assertTrue(clientesResponse[0].isActivo());
        assertEquals("Juan", clientesResponse[0].getNombres());
        assertEquals("Garcia", clientesResponse[0].getApellidos());
        assertEquals(20, clientesResponse[0].getEdad());
        assertTrue(clientesResponse[1].isActivo());
        assertEquals("Luis", clientesResponse[1].getNombres());
        assertEquals("Rodriguez", clientesResponse[1].getApellidos());
        assertEquals(20, clientesResponse[1].getEdad());
    }

    @Test
    void dadoClienteDesactivado_alActivar_entoncesCambiaEstadoActivoATrue() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(false);
        cliente.setNombres("A");
        cliente.setApellidos("A");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        // When
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.exchange("/cliente/activar/" + cliente.getIdCliente(), HttpMethod.PUT, requestEntity, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Activado correctamente.", responseEntity.getBody().getRespuesta());
        Cliente clienteActualizado = clienteRepository.findById(cliente.getIdCliente()).orElse(null);
        assertTrue(clienteActualizado.isActivo());
    }

    @Test
    void dadoClienteActivo_alDesactivar_entoncesCambiaEstadoActivoAFalse() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(true);
        cliente.setNombres("A");
        cliente.setApellidos("A");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        // When
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.exchange("/cliente/desactivar/" + cliente.getIdCliente(), HttpMethod.PUT, requestEntity, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Desactivado correctamente.", responseEntity.getBody().getRespuesta());
        Cliente clienteActualizado = clienteRepository.findById(cliente.getIdCliente()).orElse(null);
        assertFalse(clienteActualizado.isActivo());
    }
}