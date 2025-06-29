package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.data.domain.Pageable;

@Repository
public interface SeguroRepositorio extends JpaRepository<Seguro, Integer> {
    List<Seguro> findByTipoSeguro(String tipoSeguro);

    List<Seguro> findByEstado(String estado);

    List<Seguro> findByCompania(String compania);

    List<Seguro> findAllByOrderByCompaniaAsc(Pageable pageable);

    List<Seguro> findByEstadoOrderByCompaniaAsc(String estado, Pageable pageable);
}
