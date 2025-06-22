package com.banquito.core.loans.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.loans.modelo.TiposPrestamo;

@Repository
public interface TiposPrestamoRepositorio extends JpaRepository<TiposPrestamo, Integer> {

}