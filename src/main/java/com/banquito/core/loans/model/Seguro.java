package com.banquito.core.loans.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "seguros", schema = "loans")
public class Seguro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.seguros_id_seguro_seq')")
    @Column(name = "id_seguro", nullable = false)
    private Integer id;

    @Column(name = "tipo_seguro", nullable = false, length = 30)
    private String tipoSeguro;

    @Column(name = "compania", nullable = false, length = 100)
    private String compania;

    @Column(name = "monto_asegurado", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoAsegurado;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idSeguro")
    private Set<com.banquito.core.loans.model.SegurosPrestamo> segurosPrestamos = new LinkedHashSet<>();

}