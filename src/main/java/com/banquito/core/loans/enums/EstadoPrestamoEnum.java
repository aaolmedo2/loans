package com.banquito.core.loans.enums;

public enum EstadoPrestamoEnum {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    SOLICITADO("SOLICITADO");

    private final String valor;

    EstadoPrestamoEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
} 