package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "comisiones_prestamo_cliente", schema = "loans")
public class ComisionesPrestamoCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.comisiones_prestamo_cliente_id_comision_cliente_seq')")
    @Column(name = "id_comision_cliente", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo_cliente", nullable = false)
    private PrestamosCliente idPrestamoCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_comision_prestamo", nullable = false)
    private ComisionesPrestamo idComisionPrestamo;

    @Column(name = "fecha_aplicacion", nullable = false)
    private LocalDate fechaAplicacion;

    @Column(name = "monto", nullable = false, precision = 15, scale = 2)
    private BigDecimal monto;

    @ColumnDefault("'PENDIENTE'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @ColumnDefault("1")
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    public ComisionesPrestamoCliente() {
    }

    public ComisionesPrestamoCliente(Integer id) {
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

    public ComisionesPrestamo getIdComisionPrestamo() {
        return idComisionPrestamo;
    }

    public void setIdComisionPrestamo(ComisionesPrestamo idComisionPrestamo) {
        this.idComisionPrestamo = idComisionPrestamo;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
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
        ComisionesPrestamoCliente other = (ComisionesPrestamoCliente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ComisionesPrestamoCliente [id=" + id + ", idPrestamoCliente=" + idPrestamoCliente
                + ", idComisionPrestamo=" + idComisionPrestamo + ", fechaAplicacion=" + fechaAplicacion + ", monto="
                + monto + ", estado=" + estado + ", version=" + version + "]";
    }
}