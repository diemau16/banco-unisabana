package com.banco.sucursal.integration;

import com.banco.sucursal.controller.dto.ClienteDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClienteControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void alAgregarClienteEntoncesDevuelveRespuestaExitosa() {
        ClienteDTO clienteDTO = new ClienteDTO("Carlos", "Gomez", 43);
        ResponseEntity<RespuestaDTO> respuesta = restTemplate.postForEntity("/cliente/agregar", clienteDTO, RespuestaDTO.class);
        assertEquals("Cliente creado correctamente.", respuesta.getBody().getRespuesta());
    }
}