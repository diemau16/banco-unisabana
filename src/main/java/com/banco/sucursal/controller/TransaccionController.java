package com.banco.sucursal.controller;

import com.banco.sucursal.controller.dto.RespuestaDTO;
import com.banco.sucursal.controller.dto.TransaccionDTO;
import com.banco.sucursal.logica.TransaccionLogica;
import com.banco.sucursal.persistencia.Transaccion;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransaccionController {

    private TransaccionLogica transaccionLogica;

    public TransaccionController(TransaccionLogica transaccionLogica) {
        this.transaccionLogica = transaccionLogica;
    }

    @PostMapping(path = "/transaccion/nueva")
    public RespuestaDTO realizarTransaccion(@RequestBody TransaccionDTO transaccion) {
        try {
            transaccionLogica.realizarTransaccion(transaccion);
            return new RespuestaDTO("Transaccion realizada correctamente.");
        } catch (IllegalArgumentException e) {
            return new RespuestaDTO("La transaccion no se pudo realizar: " + e.getMessage());
        }
    }

    @GetMapping(path = "/transaccion/consultar")
    public List<Transaccion> obtenerTransaccion() {
        return transaccionLogica.obtenerTransacciones();
    }
}