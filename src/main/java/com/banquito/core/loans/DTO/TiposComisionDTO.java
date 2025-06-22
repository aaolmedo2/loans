package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TiposComisionDTO {
    private Integer id;
    private String tipo;
    private String nombre;
    private String descripcion;
    private String tipoCalculo;
    private BigDecimal monto;
    private String estado;
    private BigDecimal version;
}
