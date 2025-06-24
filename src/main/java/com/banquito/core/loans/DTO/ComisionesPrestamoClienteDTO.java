package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ComisionesPrestamoClienteDTO {
    private Integer id;
    private Integer idPrestamoCliente;
    private Integer idComisionPrestamo;
    private LocalDate fechaAplicacion;
    private BigDecimal monto;
    private String estado;
    private Long version;
}
