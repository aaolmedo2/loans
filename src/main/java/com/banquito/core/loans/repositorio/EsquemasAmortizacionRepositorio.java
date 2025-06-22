package com.banquito.core.loans.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.loans.modelo.EsquemasAmortizacion;

@Repository
public interface EsquemasAmortizacionRepositorio extends JpaRepository<EsquemasAmortizacion, Integer> {

}