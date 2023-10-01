package com.banco.sucursal.integration;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.persistencia.Cliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class ClienteControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void alAgregarClienteEntoncesDevuelveRespuestaExitosa() {
        ClienteDTO clienteDTO = new ClienteDTO("Carlos", "Gomez", 43);
        ResponseEntity<RespuestaDTO> respuesta = restTemplate.postForEntity("/cliente/agregar", clienteDTO, RespuestaDTO.class);
        assertEquals("Cliente creado correctamente.", respuesta.getBody().getRespuesta());
    }

    @Test
    void obtenerClientes() {
        ResponseEntity<List<Cliente>> respuesta = restTemplate.exchange("cliente/obtener", HttpMethod.GET, null, new ParameterizedTypeReference<List<Cliente>>() {});
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }
}