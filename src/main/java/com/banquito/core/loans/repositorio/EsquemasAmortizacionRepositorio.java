package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.EsquemasAmortizacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EsquemasAmortizacionRepositorio extends JpaRepository<EsquemasAmortizacion, Integer> {
    Optional<EsquemasAmortizacion> findByNombre(String nombre);

    List<EsquemasAmortizacion> findByEstado(String estado);

    List<EsquemasAmortizacion> findByIdTipoPrestamo_Id(Integer idTipoPrestamo);
}
