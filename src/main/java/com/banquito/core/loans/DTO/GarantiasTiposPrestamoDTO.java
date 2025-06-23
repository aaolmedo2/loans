package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GarantiasTiposPrestamoDTO {
    private Integer id;
    private Integer idGarantia;
    private Integer idTipoPrestamo;
    private String estado;
    private Long version;
} 