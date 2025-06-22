package com.banquito.core.loans.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EsquemasAmortizacionDTO {
    private Integer id;
    private Integer idTipoPrestamo;
    private String nombre;
    private String descripcion;
    private Boolean permiteGracia;
    private String estado;
    private BigDecimal version;
}
