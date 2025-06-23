package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.Garantia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarantiaRepository extends JpaRepository<Garantia, Integer> {
    List<Garantia> findByTipoGarantia(String tipoGarantia);

    List<Garantia> findByEstado(String estado);
}
