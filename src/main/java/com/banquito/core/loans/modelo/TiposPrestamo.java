package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "tipos_prestamos", schema = "loans")
public class TiposPrestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.tipos_prestamos_id_tipo_prestamo_seq')")
    @Column(name = "id_tipo_prestamo", nullable = false)
    private Integer id;

    @Column(name = "id_moneda", nullable = false, length = 3)
    private String idMoneda;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "requisitos", nullable = false, length = 300)
    private String requisitos;

    @Column(name = "tipo_cliente", nullable = false, length = 15)
    private String tipoCliente;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "fecha_modificacion", nullable = false)
    private Instant fechaModificacion;

    @ColumnDefault("'ACTIVO'")
    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idTipoPrestamo")
    private Set<EsquemasAmortizacion> esquemasAmortizacions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTipoPrestamo")
    private Set<GarantiasTiposPrestamo> garantiasTiposPrestamos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idTipoPrestamo")
    private Set<Prestamo> prestamos = new LinkedHashSet<>();

    public TiposPrestamo() {
    }

    public TiposPrestamo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Instant fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
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

    public Set<EsquemasAmortizacion> getEsquemasAmortizacions() {
        return esquemasAmortizacions;
    }

    public void setEsquemasAmortizacions(Set<EsquemasAmortizacion> esquemasAmortizacions) {
        this.esquemasAmortizacions = esquemasAmortizacions;
    }

    public Set<GarantiasTiposPrestamo> getGarantiasTiposPrestamos() {
        return garantiasTiposPrestamos;
    }

    public void setGarantiasTiposPrestamos(Set<GarantiasTiposPrestamo> garantiasTiposPrestamos) {
        this.garantiasTiposPrestamos = garantiasTiposPrestamos;
    }

    public Set<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(Set<Prestamo> prestamos) {
        this.prestamos = prestamos;
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
        TiposPrestamo other = (TiposPrestamo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TiposPrestamo [id=" + id + ", idMoneda=" + idMoneda + ", nombre=" + nombre + ", descripcion="
                + descripcion + ", requisitos=" + requisitos + ", tipoCliente=" + tipoCliente + ", fechaCreacion="
                + fechaCreacion + ", fechaModificacion=" + fechaModificacion + ", estado=" + estado + ", version="
                + version + ", esquemasAmortizacions=" + esquemasAmortizacions + ", garantiasTiposPrestamos="
                + garantiasTiposPrestamos + ", prestamos=" + prestamos + "]";
    }

}