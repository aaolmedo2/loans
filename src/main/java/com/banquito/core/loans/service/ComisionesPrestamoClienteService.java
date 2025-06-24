package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.ComisionesPrestamoClienteDTO;
import com.banquito.core.loans.enums.EstadoComisionClienteEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.modelo.ComisionesPrestamo;
import com.banquito.core.loans.modelo.ComisionesPrestamoCliente;
import com.banquito.core.loans.modelo.PrestamosCliente;
import com.banquito.core.loans.repositorio.ComisionesPrestamoClienteRepositorio;
import com.banquito.core.loans.repositorio.ComisionesPrestamoRepositorio;
import com.banquito.core.loans.repositorio.PrestamosClienteRepositorio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ComisionesPrestamoClienteService {

    private final ComisionesPrestamoClienteRepositorio comisionesPrestamoClienteRepositorio;
    private final ComisionesPrestamoRepositorio comisionesPrestamoRepositorio;
    private final PrestamosClienteRepositorio prestamosClienteRepository;

    public ComisionesPrestamoClienteService(ComisionesPrestamoClienteRepositorio comisionesPrestamoClienteRepositorio,
            ComisionesPrestamoRepositorio comisionesPrestamoRepositorio,
            PrestamosClienteRepositorio prestamosClienteRepository) {
        this.comisionesPrestamoClienteRepositorio = comisionesPrestamoClienteRepositorio;
        this.comisionesPrestamoRepositorio = comisionesPrestamoRepositorio;
        this.prestamosClienteRepository = prestamosClienteRepository;
    }

    public List<ComisionesPrestamoClienteDTO> obtenerTodos() {
        log.info("Obteniendo todas las comisiones de préstamos de clientes");
        List<ComisionesPrestamoCliente> comisionesClientes = this.comisionesPrestamoClienteRepositorio.findAll();
        List<ComisionesPrestamoClienteDTO> comisionesClienteDTO = new ArrayList<>();
        for (ComisionesPrestamoCliente comision : comisionesClientes) {
            comisionesClienteDTO.add(this.transformarADTO(comision));
        }
        return comisionesClienteDTO;
    }

    public ComisionesPrestamoClienteDTO obtenerPorId(Integer id) {
        log.info("Obteniendo comisión de préstamo de cliente por ID: {}", id);
        Optional<ComisionesPrestamoCliente> comisionOpt = this.comisionesPrestamoClienteRepositorio.findById(id);
        if (comisionOpt.isPresent()) {
            return this.transformarADTO(comisionOpt.get());
        } else {
            throw new EntityNotFoundException("Comisión de Préstamo de Cliente",
                    "No se encontró la comisión de préstamo de cliente con id: " + id);
        }
    }

    @Transactional
    public ComisionesPrestamoClienteDTO crear(ComisionesPrestamoClienteDTO comisionesPrestamoClienteDTO) {
        log.info("Creando nueva comisión de préstamo de cliente: {}", comisionesPrestamoClienteDTO);
        try {
            // Validar que el préstamo del cliente exista
            Optional<PrestamosCliente> prestamoClienteOpt = this.prestamosClienteRepository
                    .findById(comisionesPrestamoClienteDTO.getIdPrestamoCliente());
            if (!prestamoClienteOpt.isPresent()) {
                throw new CreateException("Comisión de Préstamo de Cliente",
                        "No existe el préstamo de cliente con id: "
                                + comisionesPrestamoClienteDTO.getIdPrestamoCliente());
            }

            // Validar que la comisión del préstamo exista
            Optional<ComisionesPrestamo> comisionPrestamoOpt = this.comisionesPrestamoRepositorio
                    .findById(comisionesPrestamoClienteDTO.getIdComisionPrestamo());
            if (!comisionPrestamoOpt.isPresent()) {
                throw new CreateException("Comisión de Préstamo de Cliente",
                        "No existe la comisión de préstamo con id: "
                                + comisionesPrestamoClienteDTO.getIdComisionPrestamo());
            }

            PrestamosCliente prestamoCliente = prestamoClienteOpt.get();
            ComisionesPrestamo comisionPrestamo = comisionPrestamoOpt.get();

            // Validar que la comisión del préstamo corresponda al mismo préstamo del
            // cliente
            if (!comisionPrestamo.getIdPrestamo().getId().equals(prestamoCliente.getIdPrestamo().getId())) {
                throw new CreateException("Comisión de Préstamo de Cliente",
                        "La comisión de préstamo no corresponde al préstamo del cliente");
            }

            ComisionesPrestamoCliente comisionCliente = new ComisionesPrestamoCliente();
            comisionCliente.setIdPrestamoCliente(prestamoCliente);
            comisionCliente.setIdComisionPrestamo(comisionPrestamo);
            // Establecer la fecha de aplicación como la fecha actual si no se proporciona
            // una
            comisionCliente.setFechaAplicacion(comisionesPrestamoClienteDTO.getFechaAplicacion() != null
                    ? comisionesPrestamoClienteDTO.getFechaAplicacion()
                    : LocalDate.now());
            // Establecer el monto de la comisión según el valor definido en TiposComisione
            comisionCliente.setMonto(comisionPrestamo.getIdTipoComision().getMonto());
            comisionCliente.setEstado(EstadoComisionClienteEnum.PENDIENTE.getValor());
            comisionCliente.setVersion(1L);

            ComisionesPrestamoCliente comisionGuardada = this.comisionesPrestamoClienteRepositorio
                    .save(comisionCliente);
            return this.transformarADTO(comisionGuardada);
        } catch (Exception e) {
            log.error("Error al crear la comisión de préstamo de cliente", e);
            throw new CreateException("Comisión de Préstamo de Cliente",
                    "Error al crear la comisión de préstamo de cliente: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Cambiar estado a cancelada para comisión de préstamo de cliente con ID: {}", id);
        Optional<ComisionesPrestamoCliente> comisionClienteOpt = this.comisionesPrestamoClienteRepositorio.findById(id);
        if (comisionClienteOpt.isPresent()) {
            ComisionesPrestamoCliente comisionCliente = comisionClienteOpt.get();
            comisionCliente.setEstado(EstadoComisionClienteEnum.CANCELADA.getValor());
            this.comisionesPrestamoClienteRepositorio.save(comisionCliente);
        } else {
            throw new EntityNotFoundException("Comisión de Préstamo de Cliente",
                    "No se encontró la comisión de préstamo de cliente con id: " + id);
        }
    }

    private ComisionesPrestamoClienteDTO transformarADTO(ComisionesPrestamoCliente comisionesPrestamoCliente) {
        return ComisionesPrestamoClienteDTO.builder()
                .id(comisionesPrestamoCliente.getId())
                .idPrestamoCliente(comisionesPrestamoCliente.getIdPrestamoCliente().getId())
                .idComisionPrestamo(comisionesPrestamoCliente.getIdComisionPrestamo().getId())
                .fechaAplicacion(comisionesPrestamoCliente.getFechaAplicacion())
                .monto(comisionesPrestamoCliente.getMonto())
                .estado(comisionesPrestamoCliente.getEstado())
                .version(comisionesPrestamoCliente.getVersion())
                .build();
    }
}
