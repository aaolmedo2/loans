package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private PrestamosCliente idPrestamoCliente;

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

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public GarantiasTiposPrestamosCliente() {
    }

    public GarantiasTiposPrestamosCliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PrestamosCliente getIdPrestamoCliente() {
        return idPrestamoCliente;
    }

    public void setIdPrestamoCliente(PrestamosCliente idPrestamoCliente) {
        this.idPrestamoCliente = idPrestamoCliente;
    }

    public GarantiasTiposPrestamo getIdGarantiaTipoPrestamo() {
        return idGarantiaTipoPrestamo;
    }

    public void setIdGarantiaTipoPrestamo(GarantiasTiposPrestamo idGarantiaTipoPrestamo) {
        this.idGarantiaTipoPrestamo = idGarantiaTipoPrestamo;
    }

    public BigDecimal getMontoTasado() {
        return montoTasado;
    }

    public void setMontoTasado(BigDecimal montoTasado) {
        this.montoTasado = montoTasado;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumentoReferencia() {
        return documentoReferencia;
    }

    public void setDocumentoReferencia(String documentoReferencia) {
        this.documentoReferencia = documentoReferencia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GarantiasTiposPrestamosCliente other = (GarantiasTiposPrestamosCliente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GarantiasTiposPrestamosCliente [id=" + id + ", idPrestamoCliente=" + idPrestamoCliente
                + ", idGarantiaTipoPrestamo=" + idGarantiaTipoPrestamo + ", montoTasado=" + montoTasado
                + ", fechaRegistro=" + fechaRegistro + ", descripcion=" + descripcion + ", documentoReferencia="
                + documentoReferencia + ", estado=" + estado + ", version=" + version + "]";
    }

}