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
    
    public boolean iniciarPago() {
        this.estadoInterno = EstadoPago.PENDIENTE;
        return true;
    }
    
    public boolean confirmarPagoExt() {
        if (this.estadoInterno == EstadoPago.PENDIENTE) {
            this.estadoInterno = EstadoPago.COMPLETADO;
            return true;
        }
        return false;
    }
    
    public EstadoPago consultarEstado() {
        return this.estadoInterno;
    }
}
