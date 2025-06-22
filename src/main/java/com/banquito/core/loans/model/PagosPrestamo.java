package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pagos_prestamos", schema = "loans")
public class PagosPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.pagos_prestamos_id_pago_seq')")
    @Column(name = "id_pago", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_cuota", nullable = false)
    private CronogramasPago idCuota;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "monto_pagado", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoPagado;

    @Column(name = "interes_pagado", nullable = false, precision = 15, scale = 2)
    private BigDecimal interesPagado;

    @ColumnDefault("0")
    @Column(name = "mora_pagada", nullable = false, precision = 15, scale = 2)
    private BigDecimal moraPagada;

    @Column(name = "capital_pagado", nullable = false, precision = 15, scale = 2)
    private BigDecimal capitalPagado;

    @Column(name = "tipo_pago", nullable = false, length = 20)
    private String tipoPago;

    @Column(name = "referencia", nullable = false, length = 100)
    private String referencia;

    @ColumnDefault("'COMPLETADO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

}