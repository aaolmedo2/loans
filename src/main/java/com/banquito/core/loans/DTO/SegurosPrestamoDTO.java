package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SegurosPrestamoDTO {
    private Integer id;
    private Integer idSeguro;
    private Integer idPrestamo;
    private String estado;
    private Long version;
} 