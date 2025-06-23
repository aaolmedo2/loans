package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.PrestamosClienteDTO;
import com.banquito.core.loans.enums.EstadoPrestamoClienteEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.modelo.Prestamo;
import com.banquito.core.loans.modelo.PrestamosCliente;
import com.banquito.core.loans.repositorio.PrestamoRepositorio;
import com.banquito.core.loans.repositorio.PrestamosClienteRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrestamosClienteService {

    private final PrestamosClienteRepositorio prestamosClienteRepository;
    private final PrestamoRepositorio prestamoRepository;

    public PrestamosClienteService(PrestamosClienteRepositorio prestamosClienteRepository,
            PrestamoRepositorio prestamoRepository) {
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

            // Validar que el monto solicitado esté dentro del rango permitido
            if (prestamoClienteDTO.getMontoSolicitado().compareTo(prestamo.getMontoMinimo()) < 0 ||
                    prestamoClienteDTO.getMontoSolicitado().compareTo(prestamo.getMontoMaximo()) > 0) {
                throw new CreateException("Préstamo de Cliente",
                        "El monto solicitado debe estar entre " + prestamo.getMontoMinimo() +
                                " y " + prestamo.getMontoMaximo());
            }

            // Validar que el plazo en meses esté dentro del rango permitido
            if (prestamoClienteDTO.getPlazoMeses() < prestamo.getPlazoMinimoMeses().intValue() ||
                    prestamoClienteDTO.getPlazoMeses() > prestamo.getPlazoMaximoMeses().intValue()) {
                throw new CreateException("Préstamo de Cliente",
                        "El plazo en meses debe estar entre " + prestamo.getPlazoMinimoMeses().intValue() +
                                " y " + prestamo.getPlazoMaximoMeses().intValue());
            } // Validar que la tasa de interés aplicada esté dentro del rango permitido
            if (prestamoClienteDTO.getTasaInteresAplicada().compareTo(java.math.BigDecimal.ZERO) < 0 ||
                    prestamoClienteDTO.getTasaInteresAplicada().compareTo(prestamo.getTasaInteres()) > 0) {
                throw new CreateException("Préstamo de Cliente",
                        "La tasa de interés aplicada debe estar en el rango de 0 a " + prestamo.getTasaInteres());
            }

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
            prestamoCliente.setVersion(1L);

            PrestamosCliente prestamoClienteGuardado = this.prestamosClienteRepository.save(prestamoCliente);
            return this.transformarADTO(prestamoClienteGuardado);
        } catch (Exception e) {
            log.error("Error al crear el préstamo de cliente", e);
            throw new CreateException("Préstamo de Cliente",
                    "Error al crear el préstamo de cliente: " + e.getMessage());
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
