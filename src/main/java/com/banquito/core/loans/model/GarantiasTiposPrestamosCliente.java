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
@Table(name = "garantias_tipos_prestamos_cliente", schema = "loans")
public class GarantiasTiposPrestamosCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.garantias_tipos_prestamos_cli_id_garantia_tipo_prestamo_cli_seq')")
    @Column(name = "id_garantia_tipo_prestamo_cliente", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo_cliente", nullable = false)
    private com.banquito.core.loans.model.PrestamosCliente idPrestamoCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_garantia_tipo_prestamo", nullable = false)
    private GarantiasTiposPrestamo idGarantiaTipoPrestamo;

    @Column(name = "monto_tasado", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoTasado;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    @Column(name = "descripcion", length = 200)
    private String descripcion;

    @Column(name = "documento_referencia", nullable = false, length = 100)
    private String documentoReferencia;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "version", nullable = false, precision = 9)
    private BigDecimal version;

}