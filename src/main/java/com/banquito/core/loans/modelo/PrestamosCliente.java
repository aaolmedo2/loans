package com.banquito.core.loans.modelo;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "prestamos_clientes", schema = "loans")
public class PrestamosCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('loans.prestamos_clientes_id_prestamo_cliente_seq')")
    @Column(name = "id_prestamo_cliente", nullable = false)
    private Integer id;

    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo idPrestamo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_aprobacion", nullable = false)
    private LocalDate fechaAprobacion;

    @Column(name = "fecha_desembolso", nullable = false)
    private LocalDate fechaDesembolso;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "monto_solicitado", nullable = false, precision = 15, scale = 2)
    private BigDecimal montoSolicitado;

    @Column(name = "plazo_meses", nullable = false)
    private Integer plazoMeses;

    @Column(name = "tasa_interes_aplicada", nullable = false, precision = 5, scale = 2)
    private BigDecimal tasaInteresAplicada;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @Version
    @Column(name = "version", nullable = false, precision = 9)
    private Long version;

    @OneToMany(mappedBy = "idPrestamoCliente")
    private Set<CronogramasPago> cronogramasPagos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPrestamoCliente")
    private Set<GarantiasTiposPrestamosCliente> garantiasTiposPrestamosClientes = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idPrestamoCliente")
    private Set<SegurosPrestamoCliente> segurosPrestamoClientes = new LinkedHashSet<>();

    public PrestamosCliente() {
    }

    public PrestamosCliente(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Prestamo getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(Prestamo idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public LocalDate getFechaDesembolso() {
        return fechaDesembolso;
    }

    public void setFechaDesembolso(LocalDate fechaDesembolso) {
        this.fechaDesembolso = fechaDesembolso;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMontoSolicitado() {
        return montoSolicitado;
    }

    public void setMontoSolicitado(BigDecimal montoSolicitado) {
        this.montoSolicitado = montoSolicitado;
    }

    public Integer getPlazoMeses() {
        return plazoMeses;
    }

    public void setPlazoMeses(Integer plazoMeses) {
        this.plazoMeses = plazoMeses;
    }

    public BigDecimal getTasaInteresAplicada() {
        return tasaInteresAplicada;
    }

    public void setTasaInteresAplicada(BigDecimal tasaInteresAplicada) {
        this.tasaInteresAplicada = tasaInteresAplicada;
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

    public Set<CronogramasPago> getCronogramasPagos() {
        return cronogramasPagos;
    }

    public void setCronogramasPagos(Set<CronogramasPago> cronogramasPagos) {
        this.cronogramasPagos = cronogramasPagos;
    }

    public Set<GarantiasTiposPrestamosCliente> getGarantiasTiposPrestamosClientes() {
        return garantiasTiposPrestamosClientes;
    }

    public void setGarantiasTiposPrestamosClientes(
            Set<GarantiasTiposPrestamosCliente> garantiasTiposPrestamosClientes) {
        this.garantiasTiposPrestamosClientes = garantiasTiposPrestamosClientes;
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
        PrestamosCliente other = (PrestamosCliente) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "PrestamosCliente [id=" + id + ", idCliente=" + idCliente + ", idPrestamo=" + idPrestamo
                + ", fechaInicio=" + fechaInicio + ", fechaAprobacion=" + fechaAprobacion + ", fechaDesembolso="
                + fechaDesembolso + ", fechaVencimiento=" + fechaVencimiento + ", montoSolicitado=" + montoSolicitado
                + ", plazoMeses=" + plazoMeses + ", tasaInteresAplicada=" + tasaInteresAplicada + ", estado=" + estado
                + ", version=" + version + ", cronogramasPagos=" + cronogramasPagos
                + ", garantiasTiposPrestamosClientes=" + garantiasTiposPrestamosClientes + ", segurosPrestamoClientes="
                + segurosPrestamoClientes + "]";
    }

}