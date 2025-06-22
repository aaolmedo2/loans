package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "prestamos", schema = "loans")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.prestamos_id_prestamo_seq')")
    @Column(name = "id_prestamo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_tipo_prestamo", nullable = false)
    private com.banquito.core.loans.model.TiposPrestamo idTipoPrestamo;

    @Column(name = "id_moneda", nullable = false, length = 3)
    private String idMoneda;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_modificacion", nullable = false)
    private Instant fechaModificacion;

    @Column(name = "base_calculo", nullable = false, length = 10)
    private String baseCalculo;

    @Column(name = "tasa_interes", nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteres;

    @Column(name = "monto_minimo", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoMinimo;

    @Column(name = "monto_maximo", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoMaximo;

    @Column(name = "plazo_minimo_meses", nullable = false, precision = 2)
    private BigDecimal plazoMinimoMeses;

    @Column(name = "plazo_maximo_meses", nullable = false, precision = 2)
    private BigDecimal plazoMaximoMeses;

    @Column(name = "tipo_amortizacion", nullable = false, length = 20)
    private String tipoAmortizacion;

    @ColumnDefault("'SOLICITADO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idPrestamo")
    private Set<com.banquito.core.loans.model.PrestamosCliente> prestamosClientes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPrestamo")
    private Set<com.banquito.core.loans.model.SegurosPrestamo> segurosPrestamos = new LinkedHashSet<>();

}