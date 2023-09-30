package com.banco.sucursal.unit.logica;

import com.banco.sucursal.controller.dto.ProductoDTO;
import com.banco.sucursal.logica.ClienteLogica;
import com.banco.sucursal.logica.ProductoLogica;
import com.banco.sucursal.persistencia.Producto;
import com.banco.sucursal.persistencia.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoLogicaTest {

    @InjectMocks
    ProductoLogica logica;

    @Mock
    ProductoRepository repository;

    @Mock
    ClienteLogica clienteLogica;

    @Test
    void Dado_tipoProductoCero_Cuando_verificarTipo_Entonces_lanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            logica.verificarTipoProducto(0);
        });
    }

    @Test
    void Dado_tipoProductoCinco_Cuando_verificarTipo_Entonces_lanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            logica.verificarTipoProducto(5);
        });
    }

    @Test
    void Dado_tipoProductoValido_Cuando_verificarTipo_Entonces_retornarValidacion() {
        boolean resultado = logica.verificarTipoProducto(3);
        assertTrue(resultado);
    }

    @Test
    void Dado_idClienteExistente_Cuando_verificarCliente_Entonces_retornarValidacion() {
        int idClienteExistente = 1;
        when(clienteLogica.existeCliente(idClienteExistente)).thenReturn(true);
        boolean resultado = logica.verificarCliente(idClienteExistente);
        assertTrue(resultado);
    }

    @Test
    void Dado_idClienteInexistente_Cuando_verificarCliente_Entonces_lanzarExcepcion() {
        int idClienteInexistente = 1;
        when(clienteLogica.existeCliente(idClienteInexistente)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            logica.verificarCliente(idClienteInexistente);
        });
    }

    @Test
    void Dado_productos_Cuando_obtenerProductos_Entonces_listarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        Producto primerProducto = new Producto();
        primerProducto.setIdProducto(1);
        primerProducto.setSaldoProducto(100);
        primerProducto.setActivo(true);
        listaProductos.add(primerProducto);

        Producto segundoProducto = new Producto();
        segundoProducto.setIdProducto(2);
        segundoProducto.setSaldoProducto(200);
        segundoProducto.setActivo(true);
        listaProductos.add(segundoProducto);

        when(repository.findAll()).thenReturn(listaProductos);
        List<Producto> listaProductosObtenidos = logica.obtenerProductos();
        assertNotNull(listaProductosObtenidos);
        assertEquals(listaProductos.size(), listaProductosObtenidos.size());
        assertTrue(listaProductosObtenidos.containsAll(listaProductos));
    }

    @Test
    void Dado_idCliente_Cuando_obtenerProductoPorCliente_Entonces_listarProductosEncontrados() {
        int idCliente = 1;

        List<Producto> listaProductos = new ArrayList<>();
        Producto productoPrimerCliente = new Producto();
        productoPrimerCliente.setIdProducto(1);
        productoPrimerCliente.setIdCliente(idCliente);
        productoPrimerCliente.setSaldoProducto(100);
        productoPrimerCliente.setActivo(true);
        listaProductos.add(productoPrimerCliente);

        Producto productoSegundoCliente = new Producto();
        productoSegundoCliente.setIdProducto(2);
        productoSegundoCliente.setIdCliente(2);
        productoSegundoCliente.setSaldoProducto(200);
        productoSegundoCliente.setActivo(true);
        listaProductos.add(productoSegundoCliente);

        when(repository.findAll()).thenReturn(listaProductos);
        List<Producto> listaProductosObtenidos = logica.obtenerProductoPorIdCliente(idCliente);
        assertNotNull(listaProductosObtenidos);
        assertEquals(1, listaProductosObtenidos.size());
        assertEquals(productoPrimerCliente, listaProductosObtenidos.get(0));
    }

    @Test
    void Dado_producto_Cuando_encontrarProducto_Entonces_listarProducto() {
        int idProducto = 1;
        List<Producto> listaProductos = new ArrayList<>();
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idProducto);
        productoExistente.setSaldoProducto(100);
        productoExistente.setActivo(true);
        listaProductos.add(productoExistente);
        when(repository.findAll()).thenReturn(listaProductos);
        Producto productoEncontrado = logica.encontrarProducto(idProducto);
        assertNotNull(productoEncontrado);
        assertEquals(idProducto, productoEncontrado.getIdProducto());
    }

    @Test
    void Dado_productoInexistente_Cuando_noEncontrarProducto_Entonces_lanzarExcepcion() {
        int idProductoInexistente = 0;
        List<Producto> listaProductos = new ArrayList<>();
        when(repository.findAll()).thenReturn(listaProductos);
        assertThrows(ResponseStatusException.class, () -> {
            logica.encontrarProducto(idProductoInexistente);
        });
    }

    @Test
    void Dado_producto_Cuando_obtenerProductoPorId_Entonces_retornarProductoEncontrado() {
        int idProductoExistente = 1;
        List<Producto> listaProductos = new ArrayList<>();
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idProductoExistente);
        productoExistente.setSaldoProducto(100);
        productoExistente.setActivo(true);
        listaProductos.add(productoExistente);
        when(repository.findAll()).thenReturn(listaProductos);
        Producto productoObtenido = logica.obtenerProductoPorIdProducto(idProductoExistente);
        assertNotNull(productoObtenido);
        assertEquals(idProductoExistente, productoObtenido.getIdProducto());
    }

    @Test
    void Dado_productoInexistente_Cuando_obtenerProductoPorId_Entonces_lanzarExcepcion() {
        int idProductoInexistente = 1;
        List<Producto> listaProductos = new ArrayList<>();
        when(repository.findAll()).thenReturn(listaProductos);
        assertThrows(ResponseStatusException.class, () -> {
            logica.obtenerProductoPorIdProducto(idProductoInexistente);
        });
    }

    @Test
    void Dado_idProductoExistente_Cuando_busqueProducto_Entonces_retornarVerdadero() {
        int idProductoExistente = 1;
        List<Producto> listaProductos = new ArrayList<>();
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idProductoExistente);
        listaProductos.add(productoExistente);
        when(repository.findAll()).thenReturn(listaProductos);
        boolean resultado = logica.existeProducto(idProductoExistente);
        assertTrue(resultado);
    }

    @Test
    void Dado_idProductoInexistente_Cuando_busqueProducto_Entonces_retornarFalso() {
        int idProductoInexistente = 2;
        List<Producto> listaProductos = new ArrayList<>();
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(1);
        listaProductos.add(productoExistente);
        when(repository.findAll()).thenReturn(listaProductos);
        boolean resultado = logica.existeProducto(idProductoInexistente);
        assertFalse(resultado);
    }

    @Test
    void Dado_producto_Cuando_desactivarProducto_Entonces_desactivar() {
        int idProductoExistente = 1;
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idProductoExistente);
        productoExistente.setSaldoProducto(100);
        productoExistente.setActivo(true);
        when(repository.findAll()).thenReturn(List.of(productoExistente));
        logica.desactivarProducto(idProductoExistente);
        assertFalse(productoExistente.isActivo());
        verify(repository, times(1)).save(productoExistente);
    }

    @Test
    void Dado_producto_Cuando_activarProducto_Entonces_activar() {
        int idProductoExistente = 1;
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idProductoExistente);
        productoExistente.setSaldoProducto(100);
        productoExistente.setActivo(false);
        when(repository.findAll()).thenReturn(List.of(productoExistente));
        logica.activarProducto(idProductoExistente);
        assertTrue(productoExistente.isActivo());
        verify(repository, times(1)).save(productoExistente);
    }

    @Test
    void Dado_producto_Cuando_guardarProductoRepositorio_Entonces_guardar() {
        Producto producto = new Producto();
        producto.setIdProducto(1);
        producto.setIdCliente(1);
        producto.setSaldoProducto(100);
        producto.setActivo(true);
        logica.guardarBD(producto);
        verify(repository, times(1)).save(producto);
        assertNotNull(producto.getIdProducto());
        assertTrue(producto.getIdProducto() > 0);
        assertEquals(1, producto.getIdCliente());
        assertEquals(100, producto.getSaldoProducto());
        assertTrue(producto.isActivo());
    }

    @Test
    void Dado_productoDTOValido_Cuando_guardarProducto_Entonces_productoGuardado() {
        ProductoDTO productoDTO = new ProductoDTO(1, 1);
        when(clienteLogica.existeCliente(productoDTO.getIdCliente())).thenReturn(true);
        logica.guardarProducto(productoDTO);
        verify(repository, times(1)).save(any(Producto.class));
    }

    @Test
    void Dado_productoDTOInvalido_Cuando_guardarProducto_Entonces_lanzarExcepcion() {
        ProductoDTO productoDTO = new ProductoDTO(1, 5);
        when(clienteLogica.existeCliente(productoDTO.getIdCliente())).thenReturn(true);
        assertThrows(Exception.class, () -> {
            logica.guardarProducto(productoDTO);
        });
    }
}