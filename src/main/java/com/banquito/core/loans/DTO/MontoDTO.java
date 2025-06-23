package com.banquito.core.loans.DTO;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MontoDTO {
    private BigDecimal monto;
}
