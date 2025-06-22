package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.PrestamoDTO;
import com.banquito.core.loans.enums.BaseCalculoEnum;
import com.banquito.core.loans.enums.EsquemaAmortizacionEnum;
import com.banquito.core.loans.enums.EstadoPrestamoEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.Prestamo;
import com.banquito.core.loans.modelo.TiposPrestamo;
import com.banquito.core.loans.repositorio.PrestamoRepository;
import com.banquito.core.loans.repositorio.TiposPrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final TiposPrestamoRepository tiposPrestamoRepository;

    public PrestamoService(PrestamoRepository prestamoRepository,
            TiposPrestamoRepository tiposPrestamoRepository) {
        this.prestamoRepository = prestamoRepository;
        this.tiposPrestamoRepository = tiposPrestamoRepository;
    }

    public List<PrestamoDTO> obtenerTodos() {
        log.info("Obteniendo todos los préstamos");
        List<Prestamo> prestamos = this.prestamoRepository.findAll();
        List<PrestamoDTO> prestamosDTO = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            prestamosDTO.add(this.transformarADTO(prestamo));
        }
        return prestamosDTO;
    }

    public List<PrestamoDTO> buscarPorNombre(String nombre) {
        log.info("Buscando préstamos por nombre: {}", nombre);
        List<Prestamo> prestamos = this.prestamoRepository.findByNombreContainingIgnoreCase(nombre);
        List<PrestamoDTO> prestamosDTO = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            prestamosDTO.add(this.transformarADTO(prestamo));
        }
        return prestamosDTO;
    }

    public List<PrestamoDTO> obtenerPorEstado(String estado) {
        log.info("Obteniendo préstamos por estado: {}", estado);
        List<Prestamo> prestamos = this.prestamoRepository.findByEstado(estado);
        List<PrestamoDTO> prestamosDTO = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            prestamosDTO.add(this.transformarADTO(prestamo));
        }
        return prestamosDTO;
    }

    public List<PrestamoDTO> obtenerPorTipoPrestamo(Integer idTipoPrestamo) {
        log.info("Obteniendo préstamos por tipo de préstamo con ID: {}", idTipoPrestamo);
        List<Prestamo> prestamos = this.prestamoRepository.findByIdTipoPrestamo_Id(idTipoPrestamo);
        List<PrestamoDTO> prestamosDTO = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            prestamosDTO.add(this.transformarADTO(prestamo));
        }
        return prestamosDTO;
    }

    public PrestamoDTO obtenerPorId(Integer id) {
        log.info("Obteniendo préstamo por ID: {}", id);
        Optional<Prestamo> prestamoOpt = this.prestamoRepository.findById(id);
        if (prestamoOpt.isPresent()) {
            return this.transformarADTO(prestamoOpt.get());
        } else {
            throw new EntityNotFoundException("Préstamo", "No se encontró el préstamo con id: " + id);
        }
    }

    @Transactional
    public PrestamoDTO crear(PrestamoDTO prestamoDTO) {
        log.info("Creando nuevo préstamo: {}", prestamoDTO);
        try {
            Optional<TiposPrestamo> tipoPrestamoOpt = this.tiposPrestamoRepository
                    .findById(prestamoDTO.getIdTipoPrestamo());
            if (!tipoPrestamoOpt.isPresent()) {
                throw new CreateException("Préstamo",
                        "No existe el tipo de préstamo con id: " + prestamoDTO.getIdTipoPrestamo());
            }

            // Validar que la base de cálculo sea uno de los valores del enum
            boolean baseCalculoValida = false;
            for (BaseCalculoEnum baseEnum : BaseCalculoEnum.values()) {
                if (baseEnum.getValor().equals(prestamoDTO.getBaseCalculo())) {
                    baseCalculoValida = true;
                    break;
                }
            }
            if (!baseCalculoValida) {
                throw new CreateException("Préstamo",
                        "La base de cálculo debe ser uno de los siguientes valores: 30/360, 31/365");
            }

            // Validar que el tipo de amortización sea uno de los valores del enum
            boolean tipoAmortizacionValido = false;
            for (EsquemaAmortizacionEnum tipoEnum : EsquemaAmortizacionEnum.values()) {
                if (tipoEnum.getValor().equals(prestamoDTO.getTipoAmortizacion())) {
                    tipoAmortizacionValido = true;
                    break;
                }
            }
            if (!tipoAmortizacionValido) {
                throw new CreateException("Préstamo",
                        "El tipo de amortización debe ser uno de los siguientes valores: FRANCES, AMERICANO, ALEMAN");
            }

            TiposPrestamo tipoPrestamo = tipoPrestamoOpt.get();

            Prestamo prestamo = new Prestamo();
            prestamo.setIdTipoPrestamo(tipoPrestamo);
            prestamo.setIdMoneda(prestamoDTO.getIdMoneda());
            prestamo.setNombre(prestamoDTO.getNombre());
            prestamo.setDescripcion(prestamoDTO.getDescripcion());
            prestamo.setFechaModificacion(Instant.now());
            prestamo.setBaseCalculo(prestamoDTO.getBaseCalculo());
            prestamo.setTasaInteres(prestamoDTO.getTasaInteres());
            prestamo.setMontoMinimo(prestamoDTO.getMontoMinimo());
            prestamo.setMontoMaximo(prestamoDTO.getMontoMaximo());
            prestamo.setPlazoMinimoMeses(prestamoDTO.getPlazoMinimoMeses());
            prestamo.setPlazoMaximoMeses(prestamoDTO.getPlazoMaximoMeses());
            prestamo.setTipoAmortizacion(prestamoDTO.getTipoAmortizacion());
            prestamo.setEstado(EstadoPrestamoEnum.SOLICITADO.getValor());
            prestamo.setVersion(1L);

            Prestamo prestamoGuardado = this.prestamoRepository.save(prestamo);
            return this.transformarADTO(prestamoGuardado);
        } catch (Exception e) {
            log.error("Error al crear el préstamo", e);
            throw new CreateException("Préstamo", "Error al crear el préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public PrestamoDTO actualizar(Integer id, PrestamoDTO prestamoDTO) {
        log.info("Actualizando préstamo con ID: {} con datos: {}", id, prestamoDTO);
        try {
            Optional<Prestamo> prestamoOpt = this.prestamoRepository.findById(id);
            if (prestamoOpt.isPresent()) {
                Prestamo prestamo = prestamoOpt.get();

                if (!prestamo.getIdTipoPrestamo().getId().equals(prestamoDTO.getIdTipoPrestamo())) {
                    Optional<TiposPrestamo> tipoPrestamoOpt = this.tiposPrestamoRepository
                            .findById(prestamoDTO.getIdTipoPrestamo());
                    if (!tipoPrestamoOpt.isPresent()) {
                        throw new UpdateException("Préstamo",
                                "No existe el tipo de préstamo con id: " + prestamoDTO.getIdTipoPrestamo());
                    }
                    prestamo.setIdTipoPrestamo(tipoPrestamoOpt.get());
                }

                // Validar que la base de cálculo sea uno de los valores del enum
                if (prestamoDTO.getBaseCalculo() != null) {
                    boolean baseCalculoValida = false;
                    for (BaseCalculoEnum baseEnum : BaseCalculoEnum.values()) {
                        if (baseEnum.getValor().equals(prestamoDTO.getBaseCalculo())) {
                            baseCalculoValida = true;
                            break;
                        }
                    }
                    if (!baseCalculoValida) {
                        throw new UpdateException("Préstamo",
                                "La base de cálculo debe ser uno de los siguientes valores: 30/360, 31/365");
                    }
                }

                // Validar que el tipo de amortización sea uno de los valores del enum
                if (prestamoDTO.getTipoAmortizacion() != null) {
                    boolean tipoAmortizacionValido = false;
                    for (EsquemaAmortizacionEnum tipoEnum : EsquemaAmortizacionEnum.values()) {
                        if (tipoEnum.getValor().equals(prestamoDTO.getTipoAmortizacion())) {
                            tipoAmortizacionValido = true;
                            break;
                        }
                    }
                    if (!tipoAmortizacionValido) {
                        throw new UpdateException("Préstamo",
                                "El tipo de amortización debe ser uno de los siguientes valores: FRANCES, AMERICANO, ALEMAN");
                    }
                }

                prestamo.setIdMoneda(prestamoDTO.getIdMoneda());
                prestamo.setNombre(prestamoDTO.getNombre());
                prestamo.setDescripcion(prestamoDTO.getDescripcion());
                prestamo.setFechaModificacion(Instant.now());
                prestamo.setBaseCalculo(prestamoDTO.getBaseCalculo());
                prestamo.setTasaInteres(prestamoDTO.getTasaInteres());
                prestamo.setMontoMinimo(prestamoDTO.getMontoMinimo());
                prestamo.setMontoMaximo(prestamoDTO.getMontoMaximo());
                prestamo.setPlazoMinimoMeses(prestamoDTO.getPlazoMinimoMeses());
                prestamo.setPlazoMaximoMeses(prestamoDTO.getPlazoMaximoMeses());
                prestamo.setTipoAmortizacion(prestamoDTO.getTipoAmortizacion());
                if (prestamoDTO.getEstado() != null) {
                    prestamo.setEstado(prestamoDTO.getEstado());
                }

                Long newVersion = prestamo.getVersion() + 1L;
                prestamo.setVersion(newVersion);

                Prestamo prestamoActualizado = this.prestamoRepository.save(prestamo);
                return this.transformarADTO(prestamoActualizado);
            } else {
                throw new EntityNotFoundException("Préstamo", "No se encontró el préstamo con id: " + id);
            }
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar el préstamo", e);
            throw new UpdateException("Préstamo", "Error al actualizar el préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando préstamo con ID: {}", id);
        Optional<Prestamo> prestamoOpt = this.prestamoRepository.findById(id);
        if (prestamoOpt.isPresent()) {
            Prestamo prestamo = prestamoOpt.get();
            prestamo.setEstado(EstadoPrestamoEnum.INACTIVO.getValor());
            this.prestamoRepository.save(prestamo);
        } else {
            throw new EntityNotFoundException("Préstamo", "No se encontró el préstamo con id: " + id);
        }
    }

    private PrestamoDTO transformarADTO(Prestamo prestamo) {
        return PrestamoDTO.builder()
                .id(prestamo.getId())
                .idTipoPrestamo(prestamo.getIdTipoPrestamo().getId())
                .idMoneda(prestamo.getIdMoneda())
                .nombre(prestamo.getNombre())
                .descripcion(prestamo.getDescripcion())
                .fechaModificacion(prestamo.getFechaModificacion())
                .baseCalculo(prestamo.getBaseCalculo())
                .tasaInteres(prestamo.getTasaInteres())
                .montoMinimo(prestamo.getMontoMinimo())
                .montoMaximo(prestamo.getMontoMaximo())
                .plazoMinimoMeses(prestamo.getPlazoMinimoMeses())
                .plazoMaximoMeses(prestamo.getPlazoMaximoMeses())
                .tipoAmortizacion(prestamo.getTipoAmortizacion())
                .estado(prestamo.getEstado())
                .version(prestamo.getVersion())
                .build();
    }
}
