package com.banco.sucursal.controller;

import com.banco.sucursal.controller.dto.ProductoDTO;
import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.logica.ProductoLogica;
import com.banco.sucursal.persistencia.Producto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductoController {
    private ProductoLogica productoLogica;

    public ProductoController(ProductoLogica productoLogica) {
        this.productoLogica = productoLogica;
    }

    @PostMapping(path = "/producto/agregar")
    public RespuestaDTO guardarProducto(@RequestBody ProductoDTO producto) {
        productoLogica.guardarProducto(producto);
        return new RespuestaDTO("Producto creado correctamente.");
    }

    @GetMapping(path = "/producto/obtener")
    public List<Producto> obtenerProductos() {
        return productoLogica.obtenerProductos();
    }

    @GetMapping(path = "/producto/obtener/cliente/{idCliente}")
    public List<Producto> obtenerProductoPorIdCliente(@PathVariable int idCliente) {
        return productoLogica.obtenerProductoPorIdCliente(idCliente);
    }

    @GetMapping(path = "/producto/obtener/{idProducto}")
    public Producto obtenerProductoPorIdProducto(@PathVariable int idProducto) {
        return productoLogica.obtenerProductoPorIdProducto(idProducto);
    }

    @PutMapping(path = "/producto/activar/{idProducto}")
    public RespuestaDTO activarProducto(@PathVariable int idProducto) {
        try {
            productoLogica.activarProducto(idProducto);
            return new RespuestaDTO("Producto activado correctamente.");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El producto no se pudo activar: " + e.getMessage());
        }
    }

    @PutMapping(path = "/producto/desactivar/{idProducto}")
    public RespuestaDTO desactivarProducto(@PathVariable int idProducto) {
        try {
            productoLogica.desactivarProducto(idProducto);
            return new RespuestaDTO("Producto desactivado correctamente.");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("El producto no se pudo desactivar: " + e.getMessage());
        }
    }
}
