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

    @GetMapping(path = "cliente/obtener")
    public List<Producto> obtenerProductos() {
        return productoLogica.obtenerProductos();
    }

    @GetMapping(path = "/cliente/obtener/{id}")
    public Producto obtenerProductoPorIdCliente(@PathVariable int idCliente) {
        return productoLogica.obtenerProductoPorId(idCliente);
    }
}
