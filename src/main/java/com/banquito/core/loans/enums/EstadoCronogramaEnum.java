package com.banquito.core.loans.enums;

public enum EstadoCronogramaEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    PENDIENTE("PENDIENTE"),
    PAGADO("PAGADO");

    private final String valor;

    EstadoCronogramaEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 