package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.TiposPrestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TiposPrestamoRepositorio extends JpaRepository<TiposPrestamo, Integer> {
    List<TiposPrestamo> findByNombreContainingIgnoreCase(String nombre);

    List<TiposPrestamo> findByEstado(String estado);

    List<TiposPrestamo> findByTipoCliente(String tipoCliente);

    List<TiposPrestamo> findByIdMoneda(String idMoneda);
}
