package com.banquito.core.loans.repositorio;

import com.banquito.core.loans.modelo.PrestamosCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamosClienteRepositorio extends JpaRepository<PrestamosCliente, Integer> {
    List<PrestamosCliente> findByIdCliente(Integer idCliente);

    List<PrestamosCliente> findByIdPrestamo_Id(Integer idPrestamo);

    List<PrestamosCliente> findByEstado(String estado);
}
