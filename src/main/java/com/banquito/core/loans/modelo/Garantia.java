package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "garantias", schema = "loans")
public class Garantia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.garantias_id_garantia_seq')")
    @Column(name = "id_garantia", nullable = false)
    private Integer id;

    @Column(name = "tipo_garantia", nullable = false, length = 20)
    private String tipoGarantia;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idGarantia")
    private Set<GarantiasTiposPrestamo> garantiasTiposPrestamos = new LinkedHashSet<>();

    public Garantia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoGarantia() {
        return tipoGarantia;
    }

    public void setTipoGarantia(String tipoGarantia) {
        this.tipoGarantia = tipoGarantia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
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

    public Set<GarantiasTiposPrestamo> getGarantiasTiposPrestamos() {
        return garantiasTiposPrestamos;
    }

    public void setGarantiasTiposPrestamos(Set<GarantiasTiposPrestamo> garantiasTiposPrestamos) {
        this.garantiasTiposPrestamos = garantiasTiposPrestamos;
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
        Garantia other = (Garantia) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Garantia [id=" + id + ", tipoGarantia=" + tipoGarantia + ", descripcion=" + descripcion + ", valor="
                + valor + ", estado=" + estado + ", version=" + version + ", garantiasTiposPrestamos="
                + garantiasTiposPrestamos + "]";
    }

}