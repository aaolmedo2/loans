package com.banquito.core.loans.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.loans.modelo.SegurosPrestamoCliente;

@Repository
public interface SegurosPrestamoClienteRepositorio extends JpaRepository<SegurosPrestamoCliente, Integer> {

}