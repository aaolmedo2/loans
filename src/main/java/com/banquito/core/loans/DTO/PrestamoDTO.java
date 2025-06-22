package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class PrestamoDTO {
    private Integer id;
    private Integer idTipoPrestamo;
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
