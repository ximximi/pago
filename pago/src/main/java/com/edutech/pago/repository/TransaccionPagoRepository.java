package com.edutech.pago.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.pago.model.EstadoPago;
import com.edutech.pago.model.TransaccionPago;


/**
 * Repositorio para acceder a los datos de TransaccionPago
 */
@Repository
public interface TransaccionPagoRepository extends JpaRepository<TransaccionPago, String> {
    
    /**
     * Busca transacciones por id de usuario
     * @param idUsuario el id del usuario
     * @return lista de transacciones del usuario
     */
    List<TransaccionPago> findByIdUsuario(String idUsuario);
    
    /**
     * Busca transacciones por estado
     * @param estadoInterno el estado a buscar
     * @return lista de transacciones con el estado especificado
     */
    List<TransaccionPago> findByEstadoInterno(EstadoPago estadoInterno);
    
    /**
     * Busca transacciones por id de item
     * @param idItem el id del item
     * @return lista de transacciones relacionadas con el item
     */
    List<TransaccionPago> findByIdItem(String idItem);
}