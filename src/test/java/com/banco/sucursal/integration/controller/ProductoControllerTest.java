package com.banco.sucursal.integration.controller;

import com.banco.sucursal.controller.dto.ProductoDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.persistencia.Cliente;
import com.banco.sucursal.persistencia.ClienteRepository;
import com.banco.sucursal.persistencia.Producto;
import com.banco.sucursal.persistencia.ProductoRepository;
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
class ProductoControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void dadoClienteExisteYTipoProductoValido_alAgregarProducto_entoncesDevuelveRespuestaExitosaYGuardaProducto() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(true);
        cliente.setNombres("Nombres");
        cliente.setApellidos("Apellidos");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        ProductoDTO productoDTO = new ProductoDTO(1, 2);
        // When
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.postForEntity("/producto/agregar", productoDTO, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Producto creado correctamente.", responseEntity.getBody().getRespuesta());
        Producto productoGuardado = productoRepository.findById(1).orElse(null);
        assertEquals(1, productoGuardado.getIdCliente());
        assertTrue(productoGuardado.isActivo());
        assertEquals(2, productoGuardado.getTipoProducto());
        assertEquals(0, productoGuardado.getSaldoProducto());
    }

    @Test
    void dadoProductosExistentes_alObtenerProductos_entoncesDevuelveListaProductos() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(true);
        cliente.setNombres("Nombres");
        cliente.setApellidos("Apellidos");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto();
        producto1.setIdCliente(1);
        producto1.setActivo(true);
        producto1.setTipoProducto(2);
        producto1.setSaldoProducto(100);
        productos.add(producto1);
        Producto producto2 = new Producto();
        producto2.setIdCliente(1);
        producto2.setActivo(true);
        producto2.setTipoProducto(3);
        producto2.setSaldoProducto(0);
        productos.add(producto2);
        productoRepository.saveAll(productos);
        // When
        ResponseEntity<Producto[]> responseEntity = restTemplate.getForEntity("/producto/obtener", Producto[].class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Producto[] productosResponse = responseEntity.getBody();
        assert productosResponse != null;
        assertEquals(2, productosResponse.length);
        assertEquals(1, productosResponse[0].getIdCliente());
        assertTrue(productosResponse[0].isActivo());
        assertEquals(2, productosResponse[0].getTipoProducto());
        assertEquals(100, productosResponse[0].getSaldoProducto());
        assertEquals(1, productosResponse[1].getIdCliente());
        assertTrue(productosResponse[1].isActivo());
        assertEquals(3, productosResponse[1].getTipoProducto());
        assertEquals(0, productosResponse[1].getSaldoProducto());
    }

    @Test
    void dadoClientesYProductos_alObtenerProductosPorIdCliente_entoncesDevuelveListaProductosDeEseCliente() {
        // Given
        Cliente cliente1 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Nombres");
        cliente1.setApellidos("Apellidos");
        cliente1.setEdad(20);
        clienteRepository.save(cliente1);
        Cliente cliente2 = new Cliente();
        cliente1.setActivo(true);
        cliente1.setNombres("Nombres2");
        cliente1.setApellidos("Apellidos2");
        cliente1.setEdad(30);
        clienteRepository.save(cliente2);
        List<Producto> productos = new ArrayList<>();
        Producto producto11 = new Producto();
        producto11.setIdCliente(1);
        producto11.setActivo(true);
        producto11.setTipoProducto(2);
        producto11.setSaldoProducto(100);
        productos.add(producto11);
        Producto producto12 = new Producto();
        producto12.setIdCliente(1);
        producto12.setActivo(true);
        producto12.setTipoProducto(3);
        producto12.setSaldoProducto(0);
        productos.add(producto12);
        Producto producto21 = new Producto();
        producto21.setIdCliente(2);
        producto21.setActivo(true);
        producto21.setTipoProducto(1);
        producto21.setSaldoProducto(50);
        productos.add(producto21);
        Producto producto22 = new Producto();
        producto22.setIdCliente(2);
        producto22.setActivo(true);
        producto22.setTipoProducto(4);
        producto22.setSaldoProducto(0);
        productos.add(producto22);
        productoRepository.saveAll(productos);
        // When
        ResponseEntity<Producto[]> responseEntity = restTemplate.getForEntity("/producto/obtener/cliente/2", Producto[].class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Producto[] productosResponse = responseEntity.getBody();
        assert productosResponse != null;
        assertEquals(2, productosResponse.length);
        assertEquals(2, productosResponse[0].getIdCliente());
        assertTrue(productosResponse[0].isActivo());
        assertEquals(1, productosResponse[0].getTipoProducto());
        assertEquals(50, productosResponse[0].getSaldoProducto());
        assertEquals(2, productosResponse[1].getIdCliente());
        assertTrue(productosResponse[1].isActivo());
        assertEquals(4, productosResponse[1].getTipoProducto());
        assertEquals(0, productosResponse[1].getSaldoProducto());
    }

    @Test
    void dadoProductos_alObtenerProductoPorIdProducto_entoncesDevuelveProductoConEseId() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(true);
        cliente.setNombres("Nombres");
        cliente.setApellidos("Apellidos");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        List<Producto> productos = new ArrayList<>();
        Producto producto1 = new Producto();
        producto1.setIdCliente(1);
        producto1.setActivo(true);
        producto1.setTipoProducto(2);
        producto1.setSaldoProducto(100);
        productos.add(producto1);
        Producto producto2 = new Producto();
        producto2.setIdCliente(1);
        producto2.setActivo(true);
        producto2.setTipoProducto(3);
        producto2.setSaldoProducto(0);
        productos.add(producto2);
        productoRepository.saveAll(productos);
        // When
        ResponseEntity<Producto> responseEntity = restTemplate.getForEntity("/producto/obtener/1", Producto.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Producto productoResponse = responseEntity.getBody();
        assert productoResponse != null;
        assertEquals(1, productoResponse.getIdCliente());
        assertTrue(productoResponse.isActivo());
        assertEquals(2, productoResponse.getTipoProducto());
        assertEquals(100, productoResponse.getSaldoProducto());
    }

    @Test
    void dadoClienteDesactivado_alActivar_entoncesCambiaEstadoActivoATrue() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(true);
        cliente.setNombres("Nombres");
        cliente.setApellidos("Apellidos");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        Producto producto = new Producto();
        producto.setIdCliente(1);
        producto.setActivo(true);
        producto.setTipoProducto(2);
        producto.setSaldoProducto(100);
        productoRepository.save(producto);
        // When
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.exchange("/producto/activar/1", HttpMethod.PUT, requestEntity, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Producto activado correctamente.", responseEntity.getBody().getRespuesta());
        Producto productoActualizado = productoRepository.findById(1).orElse(null);
        assertTrue(productoActualizado.isActivo());
    }

    @Test
    void dadoClienteActivo_alDesactivar_entoncesCambiaEstadoActivoAFalse() {
        // Given
        Cliente cliente = new Cliente();
        cliente.setActivo(true);
        cliente.setNombres("Nombres");
        cliente.setApellidos("Apellidos");
        cliente.setEdad(20);
        clienteRepository.save(cliente);
        Producto producto = new Producto();
        producto.setIdCliente(1);
        producto.setActivo(false);
        producto.setTipoProducto(2);
        producto.setSaldoProducto(100);
        productoRepository.save(producto);
        // When
        HttpEntity<Void> requestEntity = new HttpEntity<>(null);
        ResponseEntity<RespuestaDTO> responseEntity = restTemplate.exchange("/producto/desactivar/1", HttpMethod.PUT, requestEntity, RespuestaDTO.class);
        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Producto desactivado correctamente.", responseEntity.getBody().getRespuesta());
        Producto productoActualizado = productoRepository.findById(1).orElse(null);
        assertFalse(productoActualizado.isActivo());
    }
}