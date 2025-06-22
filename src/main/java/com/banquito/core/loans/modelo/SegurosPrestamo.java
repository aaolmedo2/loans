package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "seguros_prestamos", schema = "loans")
public class SegurosPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.seguros_prestamos_id_seguro_prestamo_seq')")
    @Column(name = "id_seguro_prestamo", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_seguro", nullable = false)
    private Seguro idSeguro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo idPrestamo;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idSeguroPrestamo")
    private Set<SegurosPrestamoCliente> segurosPrestamoClientes = new LinkedHashSet<>();

    public SegurosPrestamo() {
    }

    public SegurosPrestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Seguro getIdSeguro() {
        return idSeguro;
    }

    public void setIdSeguro(Seguro idSeguro) {
        this.idSeguro = idSeguro;
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

    public Set<SegurosPrestamoCliente> getSegurosPrestamoClientes() {
        return segurosPrestamoClientes;
    }

    public void setSegurosPrestamoClientes(Set<SegurosPrestamoCliente> segurosPrestamoClientes) {
        this.segurosPrestamoClientes = segurosPrestamoClientes;
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
        SegurosPrestamo other = (SegurosPrestamo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SegurosPrestamo [id=" + id + ", idSeguro=" + idSeguro + ", idPrestamo=" + idPrestamo + ", estado="
                + estado + ", version=" + version + ", segurosPrestamoClientes=" + segurosPrestamoClientes + "]";
    }

}