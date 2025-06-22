package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class SeguroDTO {
    private Integer id;
    private String tipoSeguro;
    private String compania;
    private BigDecimal montoAsegurado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private Long version;
}
