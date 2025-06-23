package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class GarantiasTiposPrestamosClienteDTO {
    private Integer id;
    private Integer idPrestamoCliente;
    private Integer idGarantiaTipoPrestamo;
    private BigDecimal montoTasado;
    private LocalDate fechaRegistro;
    private String descripcion;
    private String documentoReferencia;
    private String estado;
    private Long version;
} 