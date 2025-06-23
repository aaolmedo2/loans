package com.banquito.core.loans.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.loans.modelo.ComisionesPrestamoCliente;
import java.util.List;

@Repository
public interface ComisionesPrestamoClienteRepositorio extends JpaRepository<ComisionesPrestamoCliente, Integer> {

    List<ComisionesPrestamoCliente> findByEstado(String estado);

    List<ComisionesPrestamoCliente> findByIdPrestamoCliente_Id(Integer idPrestamoCliente);

    List<ComisionesPrestamoCliente> findByIdComisionPrestamo_Id(Integer idComisionPrestamo);
}