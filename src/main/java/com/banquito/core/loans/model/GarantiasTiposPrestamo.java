package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "garantias_tipos_prestamos", schema = "loans")
public class GarantiasTiposPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.garantias_tipos_prestamos_id_garantia_tipo_prestamo_seq')")
    @Column(name = "id_garantia_tipo_prestamo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_garantia", nullable = false)
    private Garantia idGarantia;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_tipo_prestamo", nullable = false)
    private com.banquito.core.loans.model.TiposPrestamo idTipoPrestamo;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @ColumnDefault("0")
    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idGarantiaTipoPrestamo")
    private Set<com.banquito.core.loans.model.GarantiasTiposPrestamosCliente> garantiasTiposPrestamosClientes = new LinkedHashSet<>();

}