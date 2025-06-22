package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class TiposPrestamoDTO {
    private Integer id;
    private String idMoneda;
    private String nombre;
    private String descripcion;
    private String requisitos;
    private String tipoCliente;
    private Instant fechaCreacion;
    private Instant fechaModificacion;
    private String estado;
    private BigDecimal version;
}
