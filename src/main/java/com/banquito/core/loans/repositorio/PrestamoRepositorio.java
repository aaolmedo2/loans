package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepositorio extends JpaRepository<Prestamo, Integer> {
    List<Prestamo> findByNombreContainingIgnoreCase(String nombre);

    List<Prestamo> findByEstado(String estado);

    List<Prestamo> findByIdTipoPrestamo_Id(Integer idTipoPrestamo);

    List<Prestamo> findByIdMoneda(String idMoneda);
}
