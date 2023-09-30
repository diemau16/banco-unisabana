package com.banco.sucursal.unit.logica;

import com.banco.sucursal.logica.ClienteLogica;
import com.banco.sucursal.logica.ProductoLogica;
import com.banco.sucursal.logica.TransaccionLogica;
import com.banco.sucursal.persistencia.Producto;
import com.banco.sucursal.persistencia.Transaccion;
import com.banco.sucursal.persistencia.TransaccionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransaccionLogicaTest {

    @InjectMocks
    TransaccionLogica logica;

    @Mock
    ProductoLogica productoLogica;

    @Mock
    ClienteLogica clienteLogica;

    @Mock
    Producto producto;

    @Mock
    TransaccionRepository repository;

    @Test
    void Dado_transacciones_Cuando_obtenerTransacciones_Entonces_listarTransacciones() {
        List<Transaccion> listaTransacciones = new ArrayList<>();
        Transaccion primeraTransaccion = new Transaccion();
        primeraTransaccion.setIdTransaccion(1);
        primeraTransaccion.setTipoTransaccion(1);
        primeraTransaccion.setIdClienteOrigen(1);
        primeraTransaccion.setIdProductoOrigen(1);
        primeraTransaccion.setIdClienteDestino(2);
        primeraTransaccion.setIdProductoDestino(2);
        primeraTransaccion.setMonto(100);
        listaTransacciones.add(primeraTransaccion);

        Transaccion segundaTransaccion = new Transaccion();
        segundaTransaccion.setIdTransaccion(2);
        segundaTransaccion.setTipoTransaccion(2);
        segundaTransaccion.setIdClienteOrigen(2);
        segundaTransaccion.setIdProductoOrigen(2);
        segundaTransaccion.setIdClienteDestino(1);
        segundaTransaccion.setIdProductoDestino(1);
        segundaTransaccion.setMonto(200);
        listaTransacciones.add(segundaTransaccion);

        when(repository.findAll()).thenReturn(listaTransacciones);
        List<Transaccion> listaTransaccionesObtenidas = logica.obtenerTransacciones();
        assertNotNull(listaTransaccionesObtenidas);
        assertEquals(listaTransacciones.size(), listaTransaccionesObtenidas.size());
        assertTrue(listaTransaccionesObtenidas.containsAll(listaTransacciones));
    }

    @Test
    void Dado_idCliente_Cuando_obtenerTransaccionesPorIdCliente_Entonces_listarTransaccionesDelCliente() {
        List<Transaccion> listaTransacciones = new ArrayList<>();
        Transaccion transaccion1 = new Transaccion();
        transaccion1.setIdTransaccion(1);
        transaccion1.setIdClienteOrigen(1);
        transaccion1.setIdClienteDestino(2);
        listaTransacciones.add(transaccion1);

        Transaccion transaccion2 = new Transaccion();
        transaccion2.setIdTransaccion(2);
        transaccion2.setIdClienteOrigen(2);
        transaccion2.setIdClienteDestino(1);
        listaTransacciones.add(transaccion2);

        Transaccion transaccion3 = new Transaccion();
        transaccion3.setIdTransaccion(3);
        transaccion3.setIdClienteOrigen(3);
        transaccion3.setIdClienteDestino(4);
        listaTransacciones.add(transaccion3);

        when(repository.findAll()).thenReturn(listaTransacciones);

        List<Transaccion> listaTransaccionesObtenidas = logica.obtenerTransaccionesPorIdCliente(1);

        assertNotNull(listaTransaccionesObtenidas);
        assertEquals(2, listaTransaccionesObtenidas.size());
        assertTrue(listaTransaccionesObtenidas.contains(transaccion1));
        assertTrue(listaTransaccionesObtenidas.contains(transaccion2));
        assertFalse(listaTransaccionesObtenidas.contains(transaccion3));
    }

    @Test
    void Dado_idClienteYTipo_Cuando_obtenerTransaccionesPorClienteYTipo_Entonces_listarTransacciones() {
        List<Transaccion> listaTransacciones = new ArrayList<>();
        Transaccion primeraTransaccion = new Transaccion();
        primeraTransaccion.setIdTransaccion(1);
        primeraTransaccion.setIdClienteOrigen(1);
        primeraTransaccion.setIdClienteDestino(2);
        primeraTransaccion.setTipoTransaccion(2);
        listaTransacciones.add(primeraTransaccion);

        Transaccion segundaTransaccion = new Transaccion();
        segundaTransaccion.setIdTransaccion(2);
        segundaTransaccion.setIdClienteOrigen(2);
        segundaTransaccion.setIdClienteDestino(1);
        segundaTransaccion.setTipoTransaccion(2);
        listaTransacciones.add(segundaTransaccion);

        Transaccion terceraTransaccion = new Transaccion();
        terceraTransaccion.setIdTransaccion(3);
        terceraTransaccion.setIdClienteOrigen(3);
        terceraTransaccion.setIdClienteDestino(4);
        terceraTransaccion.setTipoTransaccion(1);
        listaTransacciones.add(terceraTransaccion);

        when(repository.findAll()).thenReturn(listaTransacciones);
        List<Transaccion> listaTransaccionesObtenidas = logica.obtenerTransaccionesPorClienteYTipo(1, 2);
        assertNotNull(listaTransaccionesObtenidas);
        assertEquals(2, listaTransaccionesObtenidas.size());
        assertTrue(listaTransaccionesObtenidas.stream().allMatch(
                t -> t.getTipoTransaccion() == 2
        ));
        assertTrue(listaTransaccionesObtenidas.stream().allMatch(
                t -> t.getIdClienteOrigen() == 1 || t.getIdClienteDestino() == 1
        ));
        for (Transaccion transaccion : listaTransaccionesObtenidas) {
            assertTrue(listaTransacciones.contains(transaccion));
        }
    }

    @Test
    void CuandoGuardarTransaccionTransferencia_EntoncesGuardaLaTransaccionn() {
        Transaccion transaccionSimulada = new Transaccion();
        transaccionSimulada.setIdTransaccion(1);
        transaccionSimulada.setHoraTransaccion(LocalDateTime.now());
        transaccionSimulada.setTipoTransaccion(1);
        transaccionSimulada.setIdClienteOrigen(1);
        transaccionSimulada.setIdProductoOrigen(1);
        transaccionSimulada.setIdClienteDestino(2);
        transaccionSimulada.setIdProductoDestino(2);
        transaccionSimulada.setMonto(100);

        when(repository.save(Mockito.any(Transaccion.class))).thenReturn(transaccionSimulada);
        logica.guardarTransaccionTransferencia();
        verify(repository).save(Mockito.any(Transaccion.class));
    }

    @Test
    void CuandoGuardarTransaccionDeposito_EntoncesGuardaLaTransaccion() {
        Transaccion transaccionSimulada = new Transaccion();
        transaccionSimulada.setIdTransaccion(2);
        transaccionSimulada.setHoraTransaccion(LocalDateTime.now());
        transaccionSimulada.setTipoTransaccion(2);
        transaccionSimulada.setIdClienteOrigen(0);
        transaccionSimulada.setIdProductoOrigen(0);
        transaccionSimulada.setIdClienteDestino(1);
        transaccionSimulada.setIdProductoDestino(1);
        transaccionSimulada.setMonto(200);

        when(repository.save(Mockito.any(Transaccion.class))).thenReturn(transaccionSimulada);
        logica.guardarTransaccionDeposito();
        verify(repository).save(Mockito.any(Transaccion.class));
    }

    @Test
    void CuandoGuardarTransaccionRetiro_EntoncesGuardaLaTransaccion() {
        Transaccion transaccionSimulada = new Transaccion();
        transaccionSimulada.setIdTransaccion(3);
        transaccionSimulada.setHoraTransaccion(LocalDateTime.now());
        transaccionSimulada.setTipoTransaccion(3);
        transaccionSimulada.setIdClienteOrigen(1);
        transaccionSimulada.setIdProductoOrigen(1);
        transaccionSimulada.setIdClienteDestino(0);
        transaccionSimulada.setIdProductoDestino(0);
        transaccionSimulada.setMonto(300);

        when(repository.save(Mockito.any(Transaccion.class))).thenReturn(transaccionSimulada);
        logica.guardarTransaccionRetiro();
        verify(repository).save(Mockito.any(Transaccion.class));
    }

    @Test
    void Dado_productoExistente_Cuando_verificarPropiedadProducto_Entonces_retornaTrue() {
        int idProducto = 1;
        Producto productoExistente = new Producto();
        productoExistente.setIdProducto(idProducto);
        productoExistente.setIdCliente(1);

        when(productoLogica.encontrarProducto(idProducto)).thenReturn(productoExistente);
        boolean propiedadProducto = logica.verificarPropiedadProducto(1, idProducto);
        assertTrue(propiedadProducto);
    }

    @Test
    void Dado_productoNoExistente_Cuando_verificarPropiedadProducto_Entonces_lanzarExcepcion() {
        int idProducto = 1;
        when(productoLogica.encontrarProducto(idProducto)).thenReturn(null);
        assertThrows(Exception.class, () -> {
            logica.verificarPropiedadProducto(1, idProducto);
        });
    }

    @Test
    void Dado_clienteYProductoExistentes_Cuando_verificarOrigen_Entonces_retornaTrue() {
        when(clienteLogica.existeCliente(0)).thenReturn(true);
        when(productoLogica.existeProducto(0)).thenReturn(true);
        boolean resultado = logica.verificarOrigen();
        assertTrue(resultado);
    }

    @Test
    void Dado_clienteInexistente_Cuando_verificarOrigen_Entonces_lanzarExcepcion() {
        when(clienteLogica.existeCliente(0)).thenReturn(false);
        assertThrows(Exception.class, () -> {
            logica.verificarOrigen();
        });
    }

    @Test
    void Dado_productoInexistente_Cuando_verificarOrigen_Entonces_lanzarExcepcion() {
        when(clienteLogica.existeCliente(0)).thenReturn(true);
        when(productoLogica.existeProducto(0)).thenReturn(false);
        assertThrows(Exception.class, () -> {
            logica.verificarOrigen();
        });
    }

    @Test
    void Dado_productoDestinoExistente_Cuando_verificarDestino_Entonces_retornarVerdadero() {
        when(productoLogica.existeProducto(0)).thenReturn(true);
        boolean resultado = logica.verificarDestino();
        assertTrue(resultado);
    }

    @Test
    void Dado_productoDestinoInexistente_Cuando_verificarDestino_Entonces_lanzarExcepcion() {
        when(productoLogica.existeProducto(0)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> {
            logica.verificarDestino();
        });
    }

    @Test
    void realizarTransaccion() {
    }
}