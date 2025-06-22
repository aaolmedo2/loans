package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tipos_prestamos", schema = "loans")
public class TiposPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.tipos_prestamos_id_tipo_prestamo_seq')")
    @Column(name = "id_tipo_prestamo", nullable = false)
    private Integer id;

    @Column(name = "id_moneda", nullable = false, length = 3)
    private String idMoneda;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "requisitos", nullable = false, length = 300)
    private String requisitos;

    @Column(name = "tipo_cliente", nullable = false, length = 15)
    private String tipoCliente;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_modificacion", nullable = false)
    private Instant fechaModificacion;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idTipoPrestamo")
    private Set<EsquemasAmortizacion> esquemasAmortizacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTipoPrestamo")
    private Set<GarantiasTiposPrestamo> garantiasTiposPrestamos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTipoPrestamo")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();

}