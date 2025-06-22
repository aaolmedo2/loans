package com.banquito.core.loans.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GarantiaDTO {
    private Integer id;
    private String tipoGarantia;
    private String descripcion;
    private BigDecimal valor;
    private String estado;
    private BigDecimal version;
}
