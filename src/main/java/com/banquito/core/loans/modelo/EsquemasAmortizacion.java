package com.banquito.core.loans.modelo;

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
@Table(name = "esquemas_amortizacion", schema = "loans", uniqueConstraints = {
        @UniqueConstraint(name = "ak_unique_nombre_esquema", columnNames = { "nombre" })
})
public class EsquemasAmortizacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.esquemas_amortizacion_id_esquema_seq')")
    @Column(name = "id_esquema", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_tipo_prestamo")
    private com.banquito.core.loans.modelo.TiposPrestamo idTipoPrestamo;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "permite_gracia", nullable = false)
    private Boolean permiteGracia = false;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

}