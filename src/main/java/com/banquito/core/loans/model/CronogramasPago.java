package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cronogramas_pagos", schema = "loans")
public class CronogramasPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.cronogramas_pagos_id_cuota_seq')")
    @Column(name = "id_cuota", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo_cliente", nullable = false)
    private com.banquito.core.loans.model.PrestamosCliente idPrestamoCliente;

    @Column(name = "numero_cuota", nullable = false)
    private Integer numeroCuota;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDate fechaProgramada;

    @Column(name = "monto_cuota", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoCuota;

    @Column(name = "interes", nullable = false, precision = 15, scale = 2)
    private BigDecimal interes;

    @ColumnDefault("0")
    @Column(name = "comisiones", nullable = false, precision = 15, scale = 2)
    private BigDecimal comisiones;

    @ColumnDefault("0")
    @Column(name = "seguros", nullable = false, precision = 15, scale = 2)
    private BigDecimal seguros;

    @Column(name = "total", nullable = false, precision = 15, scale = 2)
    private BigDecimal total;

    @Column(name = "saldo_pendiente", nullable = false, precision = 15, scale = 2)
    private BigDecimal saldoPendiente;

    @ColumnDefault("'PENDIENTE'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idCuota")
    private Set<com.banquito.core.loans.model.PagosPrestamo> pagosPrestamos = new LinkedHashSet<>();

}