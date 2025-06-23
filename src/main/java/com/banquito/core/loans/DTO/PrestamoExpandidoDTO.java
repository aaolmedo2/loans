package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class PrestamoExpandidoDTO {
    private Integer id;
    // En lugar de solo el ID del tipo de préstamo, incluimos todo el objeto
    private TiposPrestamoDTO tipoPrestamo;
    private String idMoneda;
    private String nombre;
    private String descripcion;
    private Instant fechaModificacion;
    private String baseCalculo;
    private BigDecimal tasaInteres;
    private BigDecimal montoMinimo;
    private BigDecimal montoMaximo;
    private BigDecimal plazoMinimoMeses;
    private BigDecimal plazoMaximoMeses;
    private String tipoAmortizacion;
    private String estado;
    private Long version;
}
