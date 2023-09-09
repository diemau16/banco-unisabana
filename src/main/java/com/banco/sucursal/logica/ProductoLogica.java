package com.banco.sucursal.logica;

import com.banco.sucursal.controller.dto.ProductoDTO;
import com.banco.sucursal.persistencia.Producto;
import com.banco.sucursal.persistencia.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoLogica {
    private ProductoRepository productoRepository;
    private ClienteLogica clienteLogica;

    public ProductoLogica(ProductoRepository productoRepository, ClienteLogica clienteLogica) {
        this.productoRepository = productoRepository;
        this.clienteLogica = clienteLogica;
    }

    public Producto encontrarProducto(int idProducto) {
        List<Producto> listaProducto = obtenerProductos();
        for (Producto producto : listaProducto) {
            if (idProducto == producto.getIdProducto()) {
                return producto;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el cliente.");
    }

    public boolean existeProducto(int idProducto) {
        List<Producto> listaProductos = obtenerProductos();
        for (Producto producto : listaProductos) {
            if (idProducto == producto.getIdProducto()) {
                return true;
            }
        }
        return false;
    }

    public void guardarBD(Producto producto) {
        productoRepository.save(producto);
    }

    private boolean verificarCliente(int idCliente) {
        if (clienteLogica.existeCliente(idCliente)) {
            return true;
        } else {
            throw new IllegalArgumentException("No existe el cliente.");
        }
    }

    private boolean verificarTipoProducto(int tipoProducto) {
        if (tipoProducto >= 1 && tipoProducto <= 4) {
            return true;
        } else {
            throw new IllegalArgumentException("No existe el tipo de producto.");
        }
    }

    public void guardarProducto(ProductoDTO productoDTO) {
        if (verificarCliente(productoDTO.getIdCliente()) && verificarTipoProducto(productoDTO.getTipoProducto())) {
            Producto productoBD = new Producto();
            productoBD.setIdCliente(productoDTO.getIdCliente());
            productoBD.setTipoProducto(productoDTO.getTipoProducto());
            productoBD.setSaldoProducto(0);
            productoBD.setActivo(true);
            productoRepository.save(productoBD);
        }
    }

    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }

    public List<Producto> obtenerProductoPorIdCliente(int idCliente) {
        List<Producto> listaProductos = obtenerProductos();
        List<Producto> listaProductosResultados = new ArrayList<>();
        for (Producto producto : listaProductos) {
            if (producto.getIdCliente() == idCliente) {
                listaProductosResultados.add(producto);
            }
        }
        return listaProductosResultados;
    }

    public Producto obtenerProductoPorIdProducto(int idProducto) {
        return encontrarProducto(idProducto);
    }

    public void desactivarProducto(int idProducto) {
        Producto producto = encontrarProducto(idProducto);
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    public void activarProducto(int idProducto) {
        Producto producto = encontrarProducto(idProducto);
        producto.setActivo(true);
        productoRepository.save(producto);
    }
}
