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
@Table(name = "comisiones_prestamos", schema = "loans")
public class ComisionesPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.comisiones_prestamos_id_comision_prestamo_seq')")
    @Column(name = "id_comision_prestamo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_tipo_comision", nullable = false)
    private com.banquito.core.loans.model.TiposComisione idTipoComision;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo idPrestamo;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @ColumnDefault("1")
    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

    @OneToMany(mappedBy = "idComisionPrestamo")
    private Set<ComisionesPrestamoCliente> comisionesPrestamoClientes = new LinkedHashSet<>();

}