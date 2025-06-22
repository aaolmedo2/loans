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
@Table(name = "tipos_comisiones", schema = "loans")
public class TiposComisione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.tipos_comisiones_id_tipo_comision_seq')")
    @Column(name = "id_tipo_comision", nullable = false)
    private Integer id;

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "tipo_calculo", nullable = false, length = 20)
    private String tipoCalculo;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @ColumnDefault("1")
    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idTipoComision")
    private Set<ComisionesPrestamo> comisionesPrestamos = new LinkedHashSet<>();

}