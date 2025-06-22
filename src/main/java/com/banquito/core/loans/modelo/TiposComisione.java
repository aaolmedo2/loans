package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Version
    @ColumnDefault("1")
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idTipoComision")
    private Set<ComisionesPrestamo> comisionesPrestamos = new LinkedHashSet<>();

    public TiposComisione() {
    }

    public TiposComisione(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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

    public String getTipoCalculo() {
        return tipoCalculo;
    }

    public void setTipoCalculo(String tipoCalculo) {
        this.tipoCalculo = tipoCalculo;
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

    public Set<ComisionesPrestamo> getComisionesPrestamos() {
        return comisionesPrestamos;
    }

    public void setComisionesPrestamos(Set<ComisionesPrestamo> comisionesPrestamos) {
        this.comisionesPrestamos = comisionesPrestamos;
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
        TiposComisione other = (TiposComisione) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "TiposComisione [id=" + id + ", tipo=" + tipo + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", tipoCalculo=" + tipoCalculo + ", monto=" + monto + ", estado=" + estado + ", version=" + version
                + ", comisionesPrestamos=" + comisionesPrestamos + "]";
    }

}