package com.banquito.core.loans.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.loans.modelo.ComisionesPrestamo;
import java.util.List;

@Repository
public interface ComisionesPrestamoRepositorio extends JpaRepository<ComisionesPrestamo, Integer> {

    List<ComisionesPrestamo> findByEstado(String estado);

    List<ComisionesPrestamo> findByIdPrestamo_Id(Integer idPrestamo);

    List<ComisionesPrestamo> findByIdTipoComision_Id(Integer idTipoComision);
}