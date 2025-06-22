package com.banquito.core.loans.enums;

public enum EstadoSeguroEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    VENCIDO("VENCIDO"),
    CANCELADO("CANCELADO");

    private final String valor;

    EstadoSeguroEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 