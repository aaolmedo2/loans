package com.banquito.core.loans.enums;

public enum EstadoComisionClienteEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    PENDIENTE("PENDIENTE"),
    CANCELADA("CANCELADA"),
    EXENTA("EXENTA");

    private final String valor;

    EstadoComisionClienteEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 