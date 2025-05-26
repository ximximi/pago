package com.edutech.pago.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.pago.model.EstadoPago;
import com.edutech.pago.model.TransaccionPago;
import com.edutech.pago.repository.TransaccionPagoRepository;

/**
 * Servicio para gestionar las operaciones de negocio relacionadas con TransaccionPago
 */
@Service
public class TransaccionPagoService {
    
    @Autowired
    private TransaccionPagoRepository transaccionPagoRepository;
    
    /**
     * Crea una nueva transacción de pago
     */
    public TransaccionPago crearTransaccion(TransaccionPago transaccionPago) {
        // Generar ID único si no se proporciona
        if (transaccionPago.getIdPago() == null || transaccionPago.getIdPago().isEmpty()) {
            transaccionPago.setIdPago(UUID.randomUUID().toString());
        }
        
        // Iniciar el pago
        transaccionPago.iniciarPago();
        
        return transaccionPagoRepository.save(transaccionPago);
    }
    
    /**
     * Obtiene todas las transacciones
     */
    public List<TransaccionPago> obtenerTodasLasTransacciones() {
        return transaccionPagoRepository.findAll();
    }
    
    /**
     * Obtiene una transacción por su ID
     */
    public Optional<TransaccionPago> obtenerTransaccionPorId(String id) {
        return transaccionPagoRepository.findById(id);
    }
    
    /**
     * Actualiza una transacción existente
     */
    public TransaccionPago actualizarTransaccion(String id, TransaccionPago transaccionActualizada) {
        Optional<TransaccionPago> transaccionExistente = transaccionPagoRepository.findById(id);
        
        if (transaccionExistente.isPresent()) {
            TransaccionPago transaccion = transaccionExistente.get();
            
            // Actualizar campos permitidos
            transaccion.setMontoDescuento(transaccionActualizada.getMontoDescuento());
            transaccion.setMontoCobrado(transaccionActualizada.getMontoCobrado());
            transaccion.setEstadoInterno(transaccionActualizada.getEstadoInterno());
            
            return transaccionPagoRepository.save(transaccion);
        }
        
        return null;
    }
    
    /**
     * Elimina una transacción por su ID
     */
    public boolean eliminarTransaccion(String id) {
        if (transaccionPagoRepository.existsById(id)) {
            transaccionPagoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Confirma un pago pendiente
     */
    public TransaccionPago confirmarPago(String id) {
        Optional<TransaccionPago> transaccionOpt = transaccionPagoRepository.findById(id);
        
        if (transaccionOpt.isPresent()) {
            TransaccionPago transaccion = transaccionOpt.get();
            
            if (transaccion.confirmarPagoExt()) {
                return transaccionPagoRepository.save(transaccion);
            }
        }
        
        return null;
    }
    
    /**
     * Obtiene transacciones por estado
     */
    public List<TransaccionPago> obtenerTransaccionesPorEstado(EstadoPago estado) {
        return transaccionPagoRepository.findByEstadoInterno(estado);
    }
    
    /**
     * Obtiene transacciones por usuario
     */
    public List<TransaccionPago> obtenerTransaccionesPorUsuario(String idUsuario) {
        return transaccionPagoRepository.findByIdUsuario(idUsuario);
    }
}