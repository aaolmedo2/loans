package com.banquito.core.loans.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComisionesPrestamoDTO {
    private Integer id;
    private Integer idTipoComision;
    private Integer idPrestamo;
    private String estado;
    private Long version;
}
