    public TransaccionLogica(TransaccionRepository transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    public void guardarTransaccion(TransaccionDTO transaccionDTO) {
        Transaccion transaccionBD = new Transaccion();
        transaccionBD.setIdTransaccion(transaccionDTO.getIdTransaccion());
        transaccionBD.setTipoTransaccions(transaccionDTO.getTipoTransaccion());
        transaccionBD.setIdClienteOrigen(transaccionDTO.getIdClienteOrigen());
        transaccionBD.setIdProductoOrigen(transaccionDTO.getIdProductoOrigen());
        transaccionBD.setIdClienteDestino(transaccionDTO.getIdClienteDestino());
        transaccionBD.setMonto(transaccionDTO.getMonto());
        transaccionBD.setIdProductoDestino(transaccionDTO.getIdProductoDestino());
        transaccionBD.setHoraTransaccion(LocalDate.now());
        transaccionRepository.save(transaccionBD);
    }
}
