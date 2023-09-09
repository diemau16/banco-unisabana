package com.banco.sucursal.logica;

import com.banco.sucursal.controller.dto.TransaccionDTO;
import com.banco.sucursal.persistencia.Producto;
import com.banco.sucursal.persistencia.Transaccion;
import com.banco.sucursal.persistencia.TransaccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransaccionLogica {
    private TransaccionRepository transaccionRepository;
    private ClienteLogica clienteLogica;
    private ProductoLogica productoLogica;
    Transaccion transaccionBD = new Transaccion();
    private int tipoTransaccion;
    private int idClienteOrigen;
    private int idProductoOrigen;
    private int idClienteDestino;
    private int idProductoDestino;
    private float monto;

    public TransaccionLogica(TransaccionRepository transaccionRepository, ClienteLogica clienteLogica, ProductoLogica productoLogica) {
        this.transaccionRepository = transaccionRepository;
        this.clienteLogica = clienteLogica;
        this.productoLogica = productoLogica;
    }

    public List<Transaccion> obtenerTransacciones() {
        return transaccionRepository.findAll();
    }

    public List<Transaccion> obtenerTransaccionesPorIdCliente(int idCliente) {
        List<Transaccion> listaTransacciones = transaccionRepository.findAll();
        List<Transaccion> listaResultados = new ArrayList<>();
        for (Transaccion transaccion : listaTransacciones) {
            if (transaccion.getIdClienteOrigen() == idCliente || transaccion.getIdClienteDestino() == idCliente) {
                listaResultados.add(transaccion);
            }
        }
        return listaResultados;
    }

    public List<Transaccion> obtenerTransaccionesPorClienteYTipo(int idCliente, int tipoTransaccion) {
        List<Transaccion> listaTransacciones = transaccionRepository.findAll();
        List<Transaccion> listaResultados = new ArrayList<>();
        for (Transaccion transaccion : listaTransacciones) {
            if ((transaccion.getIdClienteOrigen() == idCliente || transaccion.getIdClienteDestino() == idCliente) && transaccion.getTipoTransaccion() == tipoTransaccion) {
                listaResultados.add(transaccion);
            }
        }
        return listaResultados;
    }

    public void realizarTransaccion(TransaccionDTO transaccionDTO) {
        tipoTransaccion = transaccionDTO.getTipoTransaccion();
        idClienteOrigen = transaccionDTO.getIdClienteOrigen();
        idProductoOrigen = transaccionDTO.getIdProductoOrigen();
        idProductoDestino = transaccionDTO.getIdProductoDestino();
        monto = transaccionDTO.getMonto();

        switch (tipoTransaccion) {
            case 1 -> transferencia();
            case 2 -> deposito();
            case 3 -> retiro();
            default -> throw new IllegalArgumentException(tipoTransaccion + " no es un tipo de transaccion valido.");
        }
    }

    private boolean verificarPropiedadProducto(int idCliente, int idProducto) {
        Producto producto = productoLogica.encontrarProducto(idProducto);
        if (producto.getIdCliente() == idCliente) {
            return true;
        } else {
            throw new IllegalArgumentException("El cliente no es propietario del producto.");
        }
    }

    private boolean verificarOrigen() {
        if (clienteLogica.existeCliente(idClienteOrigen) && productoLogica.existeProducto(idProductoOrigen)) {
            return true;
        } else {
            throw new IllegalArgumentException("Los datos de origen no son validos o estan incompletos.");
        }
    }

    private boolean verificarDestino() {
        if (productoLogica.existeProducto(idProductoDestino)) {
            return true;
        } else {
            throw new IllegalArgumentException("Los datos de destino no son validos o estan incompletos.");
        }
    }

    private boolean verificarMonto() {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto tiene que ser un valor positivo.");
        } else if (monto > productoLogica.encontrarProducto(idProductoOrigen).getSaldoProducto()) {
            throw new IllegalArgumentException("El monto es mayor al dinero disponible.");
        } else {
            return true;
        }
    }

    private void transferencia() {
        if (verificarOrigen() && verificarDestino() && verificarPropiedadProducto(idClienteOrigen, idProductoOrigen) && verificarMonto()) {
            Producto productoOrigen = productoLogica.encontrarProducto(idProductoOrigen);
            Producto productoDestino = productoLogica.encontrarProducto(idProductoDestino);
            productoOrigen.setSaldoProducto(productoOrigen.getSaldoProducto() - monto);
            productoDestino.setSaldoProducto(productoDestino.getSaldoProducto() + monto);
            idClienteDestino = productoDestino.getIdCliente();
            productoLogica.guardarBD(productoOrigen);
            productoLogica.guardarBD(productoDestino);
            guardarTransaccionTransferencia();
        }
    }

    private void deposito() {
        if (verificarDestino() && monto > 0) {
            Producto productoDestino = productoLogica.encontrarProducto(idProductoDestino);
            productoDestino.setSaldoProducto(productoDestino.getSaldoProducto() + monto);
            idClienteDestino = productoDestino.getIdCliente();
            productoLogica.guardarBD(productoDestino);
            guardarTransaccionDeposito();
        } else {
            throw new IllegalArgumentException("El monto tiene que ser un valor positivo.");
        }
    }

    private void retiro() {
        if (verificarOrigen() && verificarMonto() && verificarPropiedadProducto(idClienteOrigen, idProductoOrigen)) {
            Producto productoOrigen = productoLogica.encontrarProducto(idProductoOrigen);
            productoOrigen.setSaldoProducto(productoOrigen.getSaldoProducto() - monto);
            productoLogica.guardarBD(productoOrigen);
            guardarTransaccionRetiro();
        }
    }

    private void guardarTransaccionTransferencia() {
        transaccionBD.setHoraTransaccion(LocalDateTime.now());
        transaccionBD.setTipoTransaccion(tipoTransaccion);
        transaccionBD.setIdClienteOrigen(idClienteOrigen);
        transaccionBD.setIdProductoOrigen(idProductoOrigen);
        transaccionBD.setIdClienteDestino(idClienteDestino);
        transaccionBD.setIdProductoDestino(idProductoDestino);
        transaccionBD.setMonto(monto);
        transaccionRepository.save(transaccionBD);
    }

    private void guardarTransaccionDeposito() {
        transaccionBD.setHoraTransaccion(LocalDateTime.now());
        transaccionBD.setTipoTransaccion(tipoTransaccion);
        transaccionBD.setIdClienteOrigen(0);
        transaccionBD.setIdProductoOrigen(0);
        transaccionBD.setIdClienteDestino(idClienteDestino);
        transaccionBD.setIdProductoDestino(idProductoDestino);
        transaccionBD.setMonto(monto);
        transaccionRepository.save(transaccionBD);
    }

    private void guardarTransaccionRetiro() {
        transaccionBD.setHoraTransaccion(LocalDateTime.now());
        transaccionBD.setTipoTransaccion(tipoTransaccion);
        transaccionBD.setIdClienteOrigen(idClienteOrigen);
        transaccionBD.setIdProductoOrigen(idProductoOrigen);
        transaccionBD.setIdClienteDestino(0);
        transaccionBD.setIdProductoDestino(0);
        transaccionBD.setMonto(monto);
        transaccionRepository.save(transaccionBD);
    }
}
