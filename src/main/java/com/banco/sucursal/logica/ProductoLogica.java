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
        productoBD.setTipoProducto(productoBD.getTipoProducto());
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

    public boolean eliminarProducto(int idCliente){
        List<Producto> listaProductos = obtenerProductos();
        for (Producto producto : listaProductos){
            if (producto.getIdCliente() == idCliente){
                productoRepository.delete(producto);
                return true;
            }
        }
        return false;
    }
}
