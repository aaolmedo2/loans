package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.Garantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface GarantiaRepositorio extends JpaRepository<Garantia, Integer> {
    List<Garantia> findByTipoGarantia(String tipoGarantia);

    List<Garantia> findByEstado(String estado);

    List<Garantia> findAllByOrderByDescripcionAsc(Pageable pageable);

    List<Garantia> findByEstadoOrderByDescripcionAsc(String estado, Pageable pageable);
}
