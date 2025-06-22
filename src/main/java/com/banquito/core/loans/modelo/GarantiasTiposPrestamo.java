package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private TiposPrestamo idTipoPrestamo;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @ColumnDefault("0")
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idGarantiaTipoPrestamo")
    private Set<GarantiasTiposPrestamosCliente> garantiasTiposPrestamosClientes = new LinkedHashSet<>();

    public GarantiasTiposPrestamo() {
    }

    public GarantiasTiposPrestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Garantia getIdGarantia() {
        return idGarantia;
    }

    public void setIdGarantia(Garantia idGarantia) {
        this.idGarantia = idGarantia;
    }

    public TiposPrestamo getIdTipoPrestamo() {
        return idTipoPrestamo;
    }

    public void setIdTipoPrestamo(TiposPrestamo idTipoPrestamo) {
        this.idTipoPrestamo = idTipoPrestamo;
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

    public Set<GarantiasTiposPrestamosCliente> getGarantiasTiposPrestamosClientes() {
        return garantiasTiposPrestamosClientes;
    }

    public void setGarantiasTiposPrestamosClientes(
            Set<GarantiasTiposPrestamosCliente> garantiasTiposPrestamosClientes) {
        this.garantiasTiposPrestamosClientes = garantiasTiposPrestamosClientes;
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
        GarantiasTiposPrestamo other = (GarantiasTiposPrestamo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GarantiasTiposPrestamo [id=" + id + ", idGarantia=" + idGarantia + ", idTipoPrestamo=" + idTipoPrestamo
                + ", estado=" + estado + ", version=" + version + ", garantiasTiposPrestamosClientes="
                + garantiasTiposPrestamosClientes + "]";
    }

}