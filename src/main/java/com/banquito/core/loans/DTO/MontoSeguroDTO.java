package com.banquito.core.loans.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MontoSeguroDTO {
    private BigDecimal montoAsegurado;
}
