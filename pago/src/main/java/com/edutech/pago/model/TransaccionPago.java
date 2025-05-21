package com.edutech.pago.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa una transacción de pago
 */
@Entity
@Table(name = "transacciones_pago")

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransaccionPago {
    
    @Id
    private String idPago;
    
    private String idUsuario;
    private String idItem;
    private BigDecimal montoOriginal;
    private BigDecimal montoDescuento;
    private BigDecimal montoCobrado;
    private String moneda;
    
    @Enumerated(EnumType.STRING)
    private EstadoPago estadoInterno;
    
    private LocalDateTime fechaCreacion;
    
    /**
     * Método para iniciar un proceso de pago
     * @return true si el pago se inició correctamente
     */
    public boolean iniciarPago() {
        this.estadoInterno = EstadoPago.PENDIENTE;
        return true;
    }
    
    /**
     * Método para confirmar un pago con un servicio externo
     * @return true si el pago se confirmó correctamente
     */
    public boolean confirmarPagoExt() {
        if (this.estadoInterno == EstadoPago.PENDIENTE) {
            this.estadoInterno = EstadoPago.COMPLETADO;
            return true;
        }
        return false;
    }
    
    /**
     * Método para consultar el estado actual del pago
     * @return el estado actual del pago
     */
    public EstadoPago consultarEstado() {
        return this.estadoInterno;
    }
}
