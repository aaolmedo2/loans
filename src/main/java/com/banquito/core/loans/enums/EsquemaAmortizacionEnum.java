package com.banquito.core.loans.enums;

public enum EsquemaAmortizacionEnum {
    FRANCES("FRANCES"),
    AMERICANO("AMERICANO"),
    ALEMAN("ALEMAN");

    private final String valor;

    EsquemaAmortizacionEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}