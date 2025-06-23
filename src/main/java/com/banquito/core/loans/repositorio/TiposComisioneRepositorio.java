package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.TiposComisione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiposComisioneRepositorio extends JpaRepository<TiposComisione, Integer> {
    List<TiposComisione> findByTipo(String tipo);

    List<TiposComisione> findByEstado(String estado);

    List<TiposComisione> findByNombreContainingIgnoreCase(String nombre);
}
