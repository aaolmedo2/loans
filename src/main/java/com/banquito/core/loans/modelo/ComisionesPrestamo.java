package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

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
    private TiposComisione idTipoComision;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo idPrestamo;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @ColumnDefault("1")
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idComisionPrestamo")
    private Set<ComisionesPrestamoCliente> comisionesPrestamoClientes = new LinkedHashSet<>();

    public ComisionesPrestamo() {
    }

    public ComisionesPrestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TiposComisione getIdTipoComision() {
        return idTipoComision;
    }

    public void setIdTipoComision(TiposComisione idTipoComision) {
        this.idTipoComision = idTipoComision;
    }

    public Prestamo getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Prestamo idPrestamo) {
        this.idPrestamo = idPrestamo;
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

    public Set<ComisionesPrestamoCliente> getComisionesPrestamoClientes() {
        return comisionesPrestamoClientes;
    }

    public void setComisionesPrestamoClientes(Set<ComisionesPrestamoCliente> comisionesPrestamoClientes) {
        this.comisionesPrestamoClientes = comisionesPrestamoClientes;
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
        ComisionesPrestamo other = (ComisionesPrestamo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComisionesPrestamo [id=" + id + ", idTipoComision=" + idTipoComision + ", idPrestamo=" + idPrestamo
                + ", estado=" + estado + ", version=" + version + ", comisionesPrestamoClientes="
                + comisionesPrestamoClientes + "]";
    }

}