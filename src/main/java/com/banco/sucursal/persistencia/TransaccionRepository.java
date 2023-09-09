package com.banco.sucursal.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, String> {

}
