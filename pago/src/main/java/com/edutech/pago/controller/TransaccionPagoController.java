package com.edutech.pago.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.pago.model.EstadoPago;
import com.edutech.pago.model.TransaccionPago;
import com.edutech.pago.service.TransaccionPagoService;

/**
 * Controlador REST para gestionar las operaciones de TransaccionPago
 */
@RestController
@RequestMapping("/api/pagos")
public class TransaccionPagoController {
    
    @Autowired
    private TransaccionPagoService transaccionPagoService;
    
    /**
     * Crea una nueva transacción de pago
     * @param transaccionPago la transacción a crear
     * @return la transacción creada
     */
    @PostMapping
    public ResponseEntity<TransaccionPago> crearTransaccion(@RequestBody TransaccionPago transaccionPago) {
        TransaccionPago nuevaTransaccion = transaccionPagoService.crearTransaccion(transaccionPago);
        return new ResponseEntity<>(nuevaTransaccion, HttpStatus.CREATED);
    }
    
    /**
     * Obtiene todas las transacciones
     * @return lista de todas las transacciones
     */
    @GetMapping
    public ResponseEntity<List<TransaccionPago>> obtenerTodasLasTransacciones() {
        List<TransaccionPago> transacciones = transaccionPagoService.obtenerTodasLasTransacciones();
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }
    
    /**
     * Obtiene una transacción por su ID
     * @param id el ID de la transacción
     * @return la transacción si existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransaccionPago> obtenerTransaccionPorId(@PathVariable String id) {
        Optional<TransaccionPago> transaccion = transaccionPagoService.obtenerTransaccionPorId(id);
        
        if (transaccion.isPresent()) {
            return new ResponseEntity<>(transaccion.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Actualiza una transacción existente
     * @param id el ID de la transacción a actualizar
     * @param transaccionActualizada los nuevos datos de la transacción
     * @return la transacción actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransaccionPago> actualizarTransaccion(
            @PathVariable String id, 
            @RequestBody TransaccionPago transaccionActualizada) {
        
        TransaccionPago transaccion = transaccionPagoService.actualizarTransaccion(id, transaccionActualizada);
        
        if (transaccion != null) {
            return new ResponseEntity<>(transaccion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Elimina una transacción por su ID
     * @param id el ID de la transacción a eliminar
     * @return respuesta sin contenido si se eliminó correctamente
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTransaccion(@PathVariable String id) {
        boolean eliminado = transaccionPagoService.eliminarTransaccion(id);
        
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Confirma un pago pendiente
     * @param id el ID de la transacción a confirmar
     * @return la transacción actualizada
     */
    @PostMapping("/{id}/confirmar")
    public ResponseEntity<TransaccionPago> confirmarPago(@PathVariable String id) {
        TransaccionPago transaccion = transaccionPagoService.confirmarPago(id);
        
        if (transaccion != null) {
            return new ResponseEntity<>(transaccion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Obtiene transacciones por estado
     * @param estado el estado a filtrar
     * @return lista de transacciones con el estado especificado
     */
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<TransaccionPago>> obtenerTransaccionesPorEstado(
            @PathVariable EstadoPago estado) {
        
        List<TransaccionPago> transacciones = transaccionPagoService.obtenerTransaccionesPorEstado(estado);
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }
    
    /**
     * Obtiene transacciones por usuario
     * @param idUsuario el ID del usuario
     * @return lista de transacciones del usuario
     */
    @GetMapping("/usuario")
    public ResponseEntity<List<TransaccionPago>> obtenerTransaccionesPorUsuario(
            @RequestParam String idUsuario) {
        
        List<TransaccionPago> transacciones = transaccionPagoService.obtenerTransaccionesPorUsuario(idUsuario);
        return new ResponseEntity<>(transacciones, HttpStatus.OK);
    }
}