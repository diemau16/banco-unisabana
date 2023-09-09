package com.banco.sucursal.controller;

public class TransaccionController {
    private TransaccionLogica transaccionLogica;

    public TransaccionController(TransaccionLogica transaccionLogica) {
        this.transaccionLogica = transaccionLogica;
    }

    @PostMapping(path = "/transaccion/nueva")
    public RespuestaDTO guardarTransaccion(@RequestBody TransaccionDTO transaccion) {
        transaccionLogica.guardarTransaccion(transaccion);
        return new RespuestaDTO("Transaccion realizada correctamente.");
    }
}
