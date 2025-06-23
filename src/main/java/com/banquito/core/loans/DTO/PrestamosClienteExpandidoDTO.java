package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PrestamosClienteExpandidoDTO {
    private Integer id;
    private Integer idCliente;
    // En lugar de solo el ID del préstamo, incluimos todo el objeto expandido
    private PrestamoExpandidoDTO prestamo;
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
