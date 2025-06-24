package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SegurosPrestamoClienteDTO {
    private Integer id;
    private Integer idPrestamoCliente;
    private Integer idSeguroPrestamo;
    private BigDecimal montoTotal;
    private BigDecimal montoCuota;
    private String estado;
    private Long version;
} 