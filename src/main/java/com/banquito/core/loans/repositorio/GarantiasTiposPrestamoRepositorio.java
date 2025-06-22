package com.banquito.core.loans.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.loans.modelo.GarantiasTiposPrestamo;

@Repository
public interface GarantiasTiposPrestamoRepositorio extends JpaRepository<GarantiasTiposPrestamo, Integer> {

}