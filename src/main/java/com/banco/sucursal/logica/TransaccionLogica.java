package com.banco.sucursal.logica;

import com.banco.sucursal.controller.dto.TransaccionDTO;
import com.banco.sucursal.persistencia.Transaccion;
import com.banco.sucursal.persistencia.TransaccionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransaccionLogica {
    private TransaccionRepository transaccionRepository;

    public TransaccionLogica(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public List<Transaccion> obtenerTransacciones() {
        return transaccionRepository.findAll();
    }

    public void realizarTransaccion(TransaccionDTO transaccionDTO) {
        if (transaccionDTO.getTipoTransaccion() == 0 || transaccionDTO.getIdClienteOrigen() == 0 || transaccionDTO.getIdProductoOrigen() == 0 || transaccionDTO.getIdClienteDestino() == 0 || transaccionDTO.getIdProductoDestino() == 0 || transaccionDTO.getMonto() == 0) {
            throw new IllegalArgumentException("No ha proporcionado ningun dato para la transaccion.");
        }
        if (transaccionDTO.getTipoTransaccion() == 1 && (transaccionDTO.getIdClienteOrigen() == 0 || transaccionDTO.getIdProductoOrigen() == 0 || transaccionDTO.getIdClienteDestino() == 0 || transaccionDTO.getIdProductoDestino() == 0 || transaccionDTO.getMonto() == 0)) {
            throw new IllegalArgumentException("Los datos estan incompletos");
        } else {

        }
        Transaccion transaccionBD = new Transaccion();

        transaccionBD.setTipoTransaccion(transaccionDTO.getTipoTransaccion());
        transaccionBD.setIdClienteOrigen(transaccionDTO.getIdClienteOrigen());
        transaccionBD.setIdProductoOrigen(transaccionDTO.getIdProductoOrigen());
        transaccionBD.setIdClienteDestino(transaccionDTO.getIdClienteDestino());
        transaccionBD.setIdProductoDestino(transaccionDTO.getIdProductoDestino());
        transaccionBD.setMonto(transaccionDTO.getMonto());
        transaccionBD.setHoraTransaccion(LocalDate.now());
        transaccionRepository.save(transaccionBD);
    }
}
