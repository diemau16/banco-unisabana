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

    public ProductoLogica(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
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

    public void guardarProducto(ProductoDTO productoDTO) {
        Producto productoBD = new Producto();
        productoBD.setIdCliente(productoDTO.getIdCliente());
        productoBD.setTipoProducto(productoDTO.getTipoProducto());
        productoBD.setActivo(true);
        productoRepository.save(productoBD);
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
