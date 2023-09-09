package com.banco.sucursal.logica;

import com.banco.sucursal.controller.dto.ProductoDTO;
import com.banco.sucursal.persistencia.Producto;
import com.banco.sucursal.persistencia.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoLogica {
    private ProductoRepository productoRepository;

    public ProductoLogica(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }

    public void guardarProducto(ProductoDTO productoDTO) {
        Producto productoBD = new Producto();
        productoBD.setIdCliente(productoDTO.getIdCliente());
        productoBD.setTipoProducto(productoDTO.getTipoProducto());
        productoBD.setActivo(true);
        productoRepository.save(productoBD);
    }

    public List<Producto> obtenerProductos(){
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(int idCliente){
        List<Producto> listaProductos = obtenerProductos();
        for (Producto producto : listaProductos){
            if (producto.getIdCliente() == idCliente){
                return producto;
            }
        }
        return null;
    }

    public void desactivarProducto(int idProducto) {
        List<Producto> listaProductos = obtenerProductos();
        for (Producto producto : listaProductos) {
            if (idProducto == producto.getIdProducto()) {
                producto.setActivo(false);
                productoRepository.save(producto);
                return;
            }
        }
    }

    public void activarProducto(int idProducto) {
        List<Producto> listaProductos = obtenerProductos();
        for (Producto producto : listaProductos) {
            if (idProducto == producto.getIdProducto()) {
                producto.setActivo(true);
                productoRepository.save(producto);
                return;
            }
        }
    }
}
