package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PrestamosClienteDTO {
    private Integer id;
    private Integer idCliente;
    private Integer idPrestamo;
    private LocalDate fechaInicio;
    private LocalDate fechaAprobacion;
    private LocalDate fechaDesembolso;
    private LocalDate fechaVencimiento;
    private BigDecimal montoSolicitado;
    private Integer plazoMeses;
    private BigDecimal tasaInteresAplicada;
    private String estado;
    private Long version;
}
