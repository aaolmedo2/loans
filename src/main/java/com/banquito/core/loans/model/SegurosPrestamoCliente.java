package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "seguros_prestamo_cliente", schema = "loans")
public class SegurosPrestamoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.seguros_prestamo_cliente_id_seguro_prestamo_cliente_seq')")
    @Column(name = "id_seguro_prestamo_cliente", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo_cliente", nullable = false)
    private PrestamosCliente idPrestamoCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_seguro_prestamo", nullable = false)
    private com.banquito.core.loans.model.SegurosPrestamo idSeguroPrestamo;

    @Column(name = "monto_total", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "monto_cuota", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoCuota;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

}