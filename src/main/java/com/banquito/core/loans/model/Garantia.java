package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "garantias", schema = "loans")
public class Garantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.garantias_id_garantia_seq')")
    @Column(name = "id_garantia", nullable = false)
    private Integer id;

    @Column(name = "tipo_garantia", nullable = false, length = 20)
    private String tipoGarantia;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idGarantia")
    private Set<com.banquito.core.loans.model.GarantiasTiposPrestamo> garantiasTiposPrestamos = new LinkedHashSet<>();

}