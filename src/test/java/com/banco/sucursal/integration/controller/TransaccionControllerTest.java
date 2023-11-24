package com.banco.sucursal.integration.controller;

import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.controller.dto.TransaccionDTO;
import com.banco.sucursal.persistencia.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TransaccionControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransaccionRepository transaccionRepository;

    @Test
    void dadoClientesYProductosValidos_alRealizarTransaccion_entoncesDevuelveRespuestaExitosaYGuardaTransaccion() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Nombres1");
        cliente1.setApellidos("Apellidos1");
        cliente1.setEdad(20);
        clienteRepository.save(cliente1);
        Cliente cliente2 = new Cliente();
        cliente2.setActivo(true);
        cliente2.setNombres("Nombres2");
        cliente2.setApellidos("Apellidos2");
        cliente2.setEdad(20);
        clienteRepository.save(cliente2);
        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto();
        producto1.setIdCliente(1);
        producto1.setActivo(true);
        producto1.setTipoProducto(2);
        producto1.setSaldoProducto(100);
        productos.add(producto1);
        Producto producto2 = new Producto();
        producto2.setIdCliente(2);
        producto2.setActivo(true);
        producto2.setTipoProducto(3);
        producto2.setSaldoProducto(0);
        productos.add(producto2);
        productoRepository.saveAll(productos);
        TransaccionDTO transaccionDTO = new TransaccionDTO(1, 1, 1, 2, 50);
        // When
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.postForEntity("/transaccion/nueva", transaccionDTO, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Transaccion realizada correctamente.", responseEntity.getBody().getRespuesta());
        Transaccion transaccionGuardada = transaccionRepository.findById(1).orElse(null);
        assertNotNull(transaccionGuardada.getHoraTransaccion());
        assertEquals(1, transaccionGuardada.getTipoTransaccion());
        assertEquals(1, transaccionGuardada.getIdClienteOrigen());
        assertEquals(1, transaccionGuardada.getIdProductoOrigen());
        assertEquals(2, transaccionGuardada.getIdClienteDestino());
        assertEquals(2, transaccionGuardada.getIdProductoDestino());
        assertEquals(50, transaccionGuardada.getMonto());
    }

    @Test
    void dadoTransaccionesGuardadas_alObtenerTransacciones_devuelveLasTransacciones() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Nombres1");
        cliente1.setApellidos("Apellidos1");
        cliente1.setEdad(20);
        clienteRepository.save(cliente1);
        Cliente cliente2 = new Cliente();
        cliente2.setActivo(true);
        cliente2.setNombres("Nombres2");
        cliente2.setApellidos("Apellidos2");
        cliente2.setEdad(20);
        clienteRepository.save(cliente2);
        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto();
        producto1.setIdCliente(1);
        producto1.setActivo(true);
        producto1.setTipoProducto(2);
        producto1.setSaldoProducto(100);
        productos.add(producto1);
        Producto producto2 = new Producto();
        producto2.setIdCliente(2);
        producto2.setActivo(true);
        producto2.setTipoProducto(3);
        producto2.setSaldoProducto(0);
        productos.add(producto2);
        productoRepository.saveAll(productos);
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setHoraTransaccion(LocalDateTime.now());
        transaccion1.setTipoTransaccion(2);
        transaccion1.setIdClienteOrigen(0);
        transaccion1.setIdProductoOrigen(0);
        transaccion1.setIdClienteDestino(1);
        transaccion1.setIdProductoDestino(1);
        transaccion1.setMonto(100);
        transaccionRepository.save(transaccion1);
        Transaccion transaccion2 = new Transaccion();
        transaccion2.setHoraTransaccion(LocalDateTime.now());
        transaccion2.setTipoTransaccion(1);
        transaccion2.setIdClienteOrigen(1);
        transaccion2.setIdProductoOrigen(1);
        transaccion2.setIdClienteDestino(2);
        transaccion2.setIdProductoDestino(2);
        transaccion2.setMonto(50);
        transaccionRepository.save(transaccion2);
        // When
        ResponseEntity<Transaccion[]> responseEntity = restTemplate.getForEntity("/transaccion/consultar", Transaccion[].class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Transaccion[] transaccionesResponse = responseEntity.getBody();
        assertNotNull(transaccionesResponse);
        assertEquals(2, transaccionesResponse.length);
        assertNotNull(transaccionesResponse[0].getHoraTransaccion());
        assertEquals(2, transaccionesResponse[0].getTipoTransaccion());
        assertEquals(0, transaccionesResponse[0].getIdClienteOrigen());
        assertEquals(0, transaccionesResponse[0].getIdProductoOrigen());
        assertEquals(1, transaccionesResponse[0].getIdClienteDestino());
        assertEquals(1, transaccionesResponse[0].getIdProductoDestino());
        assertEquals(100, transaccionesResponse[0].getMonto());
        assertNotNull(transaccionesResponse[1].getHoraTransaccion());
        assertEquals(1, transaccionesResponse[1].getTipoTransaccion());
        assertEquals(1, transaccionesResponse[1].getIdClienteOrigen());
        assertEquals(1, transaccionesResponse[1].getIdProductoOrigen());
        assertEquals(2, transaccionesResponse[1].getIdClienteDestino());
        assertEquals(2, transaccionesResponse[1].getIdProductoDestino());
        assertEquals(50, transaccionesResponse[1].getMonto());
    }

    @Test
    void dadoTransacciones_alObtenerTransaccionesPorIdCliente_entoncesDevuelveTransaccionesDondeEstaEseCliente() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Nombres1");
        cliente1.setApellidos("Apellidos1");
        cliente1.setEdad(20);
        clienteRepository.save(cliente1);
        Cliente cliente2 = new Cliente();
        cliente2.setActivo(true);
        cliente2.setNombres("Nombres2");
        cliente2.setApellidos("Apellidos2");
        cliente2.setEdad(20);
        clienteRepository.save(cliente2);
        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto();
        producto1.setIdCliente(1);
        producto1.setActivo(true);
        producto1.setTipoProducto(2);
        producto1.setSaldoProducto(100);
        productos.add(producto1);
        Producto producto2 = new Producto();
        producto2.setIdCliente(2);
        producto2.setActivo(true);
        producto2.setTipoProducto(3);
        producto2.setSaldoProducto(0);
        productos.add(producto2);
        productoRepository.saveAll(productos);
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setHoraTransaccion(LocalDateTime.now());
        transaccion1.setTipoTransaccion(2);
        transaccion1.setIdClienteOrigen(0);
        transaccion1.setIdProductoOrigen(0);
        transaccion1.setIdClienteDestino(1);
        transaccion1.setIdProductoDestino(1);
        transaccion1.setMonto(100);
        transaccionRepository.save(transaccion1);
        Transaccion transaccion2 = new Transaccion();
        transaccion2.setHoraTransaccion(LocalDateTime.now());
        transaccion2.setTipoTransaccion(1);
        transaccion2.setIdClienteOrigen(1);
        transaccion2.setIdProductoOrigen(1);
        transaccion2.setIdClienteDestino(2);
        transaccion2.setIdProductoDestino(2);
        transaccion2.setMonto(50);
        transaccionRepository.save(transaccion2);
        // When
        ResponseEntity<Transaccion[]> responseEntity = restTemplate.getForEntity("/transaccion/consultar/2", Transaccion[].class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Transaccion[] transaccionResponse = responseEntity.getBody();
        assertNotNull(transaccionResponse);
        assertEquals(1, transaccionResponse.length);
        assertNotNull(transaccionResponse[0].getHoraTransaccion());
        assertNotNull(transaccionResponse[0].getHoraTransaccion());
        assertEquals(1, transaccionResponse[0].getTipoTransaccion());
        assertEquals(1, transaccionResponse[0].getIdClienteOrigen());
        assertEquals(1, transaccionResponse[0].getIdProductoOrigen());
        assertEquals(2, transaccionResponse[0].getIdClienteDestino());
        assertEquals(2, transaccionResponse[0].getIdProductoDestino());
        assertEquals(50, transaccionResponse[0].getMonto());
    }

    @Test
    void testObtenerTransaccionesPorIdCliente() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Nombres1");
        cliente1.setApellidos("Apellidos1");
        cliente1.setEdad(20);
        clienteRepository.save(cliente1);
        Cliente cliente2 = new Cliente();
        cliente2.setActivo(true);
        cliente2.setNombres("Nombres2");
        cliente2.setApellidos("Apellidos2");
        cliente2.setEdad(20);
        clienteRepository.save(cliente2);
        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto();
        producto1.setIdCliente(1);
        producto1.setActivo(true);
        producto1.setTipoProducto(2);
        producto1.setSaldoProducto(100);
        productos.add(producto1);
        Producto producto2 = new Producto();
        producto2.setIdCliente(2);
        producto2.setActivo(true);
        producto2.setTipoProducto(3);
        producto2.setSaldoProducto(0);
        productos.add(producto2);
        productoRepository.saveAll(productos);
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setHoraTransaccion(LocalDateTime.now());
        transaccion1.setTipoTransaccion(2);
        transaccion1.setIdClienteOrigen(0);
        transaccion1.setIdProductoOrigen(0);
        transaccion1.setIdClienteDestino(1);
        transaccion1.setIdProductoDestino(1);
        transaccion1.setMonto(100);
        transaccionRepository.save(transaccion1);
        Transaccion transaccion2 = new Transaccion();
        transaccion2.setHoraTransaccion(LocalDateTime.now());
        transaccion2.setTipoTransaccion(1);
        transaccion2.setIdClienteOrigen(1);
        transaccion2.setIdProductoOrigen(1);
        transaccion2.setIdClienteDestino(2);
        transaccion2.setIdProductoDestino(2);
        transaccion2.setMonto(50);
        transaccionRepository.save(transaccion2);
        // When
        ResponseEntity<Transaccion[]> responseEntity = restTemplate.getForEntity("/transaccion/consultar/2/1", Transaccion[].class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Transaccion[] transaccionResponse = responseEntity.getBody();
        assertNotNull(transaccionResponse);
        assertEquals(1, transaccionResponse.length);
        assertNotNull(transaccionResponse[0].getHoraTransaccion());
        assertNotNull(transaccionResponse[0].getHoraTransaccion());
        assertEquals(1, transaccionResponse[0].getTipoTransaccion());
        assertEquals(1, transaccionResponse[0].getIdClienteOrigen());
        assertEquals(1, transaccionResponse[0].getIdProductoOrigen());
        assertEquals(2, transaccionResponse[0].getIdClienteDestino());
        assertEquals(2, transaccionResponse[0].getIdProductoDestino());
        assertEquals(50, transaccionResponse[0].getMonto());
    }
}