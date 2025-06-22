package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.PrestamosClienteDTO;
import com.banquito.core.loans.enums.EstadoPrestamoClienteEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.Prestamo;
import com.banquito.core.loans.modelo.PrestamosCliente;
import com.banquito.core.loans.repositorio.PrestamoRepository;
import com.banquito.core.loans.repositorio.PrestamosClienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrestamosClienteService {

    private final PrestamosClienteRepository prestamosClienteRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamosClienteService(PrestamosClienteRepository prestamosClienteRepository,
            PrestamoRepository prestamoRepository) {
        this.prestamosClienteRepository = prestamosClienteRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public List<PrestamosClienteDTO> obtenerTodos() {
        log.info("Obteniendo todos los préstamos de clientes");
        List<PrestamosCliente> prestamosClientes = this.prestamosClienteRepository.findAll();
        List<PrestamosClienteDTO> prestamosClientesDTO = new ArrayList<>();
        for (PrestamosCliente prestamoCliente : prestamosClientes) {
            prestamosClientesDTO.add(this.transformarADTO(prestamoCliente));
        }
        return prestamosClientesDTO;
    }

    public List<PrestamosClienteDTO> obtenerPorEstado(String estado) {
        log.info("Obteniendo préstamos de clientes por estado: {}", estado);
        List<PrestamosCliente> prestamosClientes = this.prestamosClienteRepository.findByEstado(estado);
        List<PrestamosClienteDTO> prestamosClientesDTO = new ArrayList<>();
        for (PrestamosCliente prestamoCliente : prestamosClientes) {
            prestamosClientesDTO.add(this.transformarADTO(prestamoCliente));
        }
        return prestamosClientesDTO;
    }

    public PrestamosClienteDTO obtenerPorId(Integer id) {
        log.info("Obteniendo préstamo de cliente por ID: {}", id);
        Optional<PrestamosCliente> prestamoClienteOpt = this.prestamosClienteRepository.findById(id);
        if (prestamoClienteOpt.isPresent()) {
            return this.transformarADTO(prestamoClienteOpt.get());
        } else {
            throw new EntityNotFoundException("Préstamo de Cliente",
                    "No se encontró el préstamo de cliente con id: " + id);
        }
    }

    @Transactional
    public PrestamosClienteDTO crear(PrestamosClienteDTO prestamoClienteDTO) {
        log.info("Creando nuevo préstamo de cliente: {}", prestamoClienteDTO);
        try {
            Optional<Prestamo> prestamoOpt = this.prestamoRepository.findById(prestamoClienteDTO.getIdPrestamo());
            if (!prestamoOpt.isPresent()) {
                throw new CreateException("Préstamo de Cliente",
                        "No existe el préstamo con id: " + prestamoClienteDTO.getIdPrestamo());
            }

            Prestamo prestamo = prestamoOpt.get();

            PrestamosCliente prestamoCliente = new PrestamosCliente();
            prestamoCliente.setIdCliente(prestamoClienteDTO.getIdCliente());
            prestamoCliente.setIdPrestamo(prestamo);
            prestamoCliente.setFechaInicio(prestamoClienteDTO.getFechaInicio());
            prestamoCliente.setFechaAprobacion(prestamoClienteDTO.getFechaAprobacion());
            prestamoCliente.setFechaDesembolso(prestamoClienteDTO.getFechaDesembolso());
            prestamoCliente.setFechaVencimiento(prestamoClienteDTO.getFechaVencimiento());
            prestamoCliente.setMontoSolicitado(prestamoClienteDTO.getMontoSolicitado());
            prestamoCliente.setPlazoMeses(prestamoClienteDTO.getPlazoMeses());
            prestamoCliente.setTasaInteresAplicada(prestamoClienteDTO.getTasaInteresAplicada());
            prestamoCliente.setEstado(EstadoPrestamoClienteEnum.SOLICITADO.getValor());
            prestamoCliente.setVersion(new BigDecimal(1));

            PrestamosCliente prestamoClienteGuardado = this.prestamosClienteRepository.save(prestamoCliente);
            return this.transformarADTO(prestamoClienteGuardado);
        } catch (Exception e) {
            log.error("Error al crear el préstamo de cliente", e);
            throw new CreateException("Préstamo de Cliente",
                    "Error al crear el préstamo de cliente: " + e.getMessage());
        }
    }

    @Transactional
    public PrestamosClienteDTO actualizar(Integer id, PrestamosClienteDTO prestamoClienteDTO) {
        log.info("Actualizando préstamo de cliente con ID: {} con datos: {}", id, prestamoClienteDTO);
        try {
            Optional<PrestamosCliente> prestamoClienteOpt = this.prestamosClienteRepository.findById(id);
            if (prestamoClienteOpt.isPresent()) {
                PrestamosCliente prestamoCliente = prestamoClienteOpt.get();

                prestamoCliente.setIdCliente(prestamoClienteDTO.getIdCliente());

                if (!prestamoCliente.getIdPrestamo().getId().equals(prestamoClienteDTO.getIdPrestamo())) {
                    Optional<Prestamo> prestamoOpt = this.prestamoRepository
                            .findById(prestamoClienteDTO.getIdPrestamo());
                    if (!prestamoOpt.isPresent()) {
                        throw new UpdateException("Préstamo de Cliente",
                                "No existe el préstamo con id: " + prestamoClienteDTO.getIdPrestamo());
                    }
                    prestamoCliente.setIdPrestamo(prestamoOpt.get());
                }

                prestamoCliente.setFechaInicio(prestamoClienteDTO.getFechaInicio());
                prestamoCliente.setFechaAprobacion(prestamoClienteDTO.getFechaAprobacion());
                prestamoCliente.setFechaDesembolso(prestamoClienteDTO.getFechaDesembolso());
                prestamoCliente.setFechaVencimiento(prestamoClienteDTO.getFechaVencimiento());
                prestamoCliente.setMontoSolicitado(prestamoClienteDTO.getMontoSolicitado());
                prestamoCliente.setPlazoMeses(prestamoClienteDTO.getPlazoMeses());
                prestamoCliente.setTasaInteresAplicada(prestamoClienteDTO.getTasaInteresAplicada());

                if (prestamoClienteDTO.getEstado() != null) {
                    prestamoCliente.setEstado(prestamoClienteDTO.getEstado());
                }

                BigDecimal newVersion = prestamoCliente.getVersion().add(new BigDecimal(1));
                prestamoCliente.setVersion(newVersion);

                PrestamosCliente prestamoClienteActualizado = this.prestamosClienteRepository.save(prestamoCliente);
                return this.transformarADTO(prestamoClienteActualizado);
            } else {
                throw new EntityNotFoundException("Préstamo de Cliente",
                        "No se encontró el préstamo de cliente con id: " + id);
            }
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar el préstamo de cliente", e);
            throw new UpdateException("Préstamo de Cliente",
                    "Error al actualizar el préstamo de cliente: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando préstamo de cliente con ID: {}", id);
        Optional<PrestamosCliente> prestamoClienteOpt = this.prestamosClienteRepository.findById(id);
        if (prestamoClienteOpt.isPresent()) {
            PrestamosCliente prestamoCliente = prestamoClienteOpt.get();
            prestamoCliente.setEstado(EstadoPrestamoClienteEnum.INACTIVO.getValor());
            this.prestamosClienteRepository.save(prestamoCliente);
        } else {
            throw new EntityNotFoundException("Préstamo de Cliente",
                    "No se encontró el préstamo de cliente con id: " + id);
        }
    }

    private PrestamosClienteDTO transformarADTO(PrestamosCliente prestamoCliente) {
        return PrestamosClienteDTO.builder()
                .id(prestamoCliente.getId())
                .idCliente(prestamoCliente.getIdCliente())
                .idPrestamo(prestamoCliente.getIdPrestamo().getId())
                .fechaInicio(prestamoCliente.getFechaInicio())
                .fechaAprobacion(prestamoCliente.getFechaAprobacion())
                .fechaDesembolso(prestamoCliente.getFechaDesembolso())
                .fechaVencimiento(prestamoCliente.getFechaVencimiento())
                .montoSolicitado(prestamoCliente.getMontoSolicitado())
                .plazoMeses(prestamoCliente.getPlazoMeses())
                .tasaInteresAplicada(prestamoCliente.getTasaInteresAplicada())
                .estado(prestamoCliente.getEstado())
                .version(prestamoCliente.getVersion())
                .build();
    }
}
