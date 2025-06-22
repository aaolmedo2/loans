package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.TiposPrestamoDTO;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.enums.TipoClienteEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.TiposPrestamo;
import com.banquito.core.loans.repositorio.TiposPrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TiposPrestamoService {

    private final TiposPrestamoRepository tiposPrestamoRepository;

    public TiposPrestamoService(TiposPrestamoRepository tiposPrestamoRepository) {
        this.tiposPrestamoRepository = tiposPrestamoRepository;
    }

    public List<TiposPrestamoDTO> obtenerTodos() {
        log.info("Obteniendo todos los tipos de préstamo");
        List<TiposPrestamo> tiposPrestamo = this.tiposPrestamoRepository.findAll();
        List<TiposPrestamoDTO> tiposPrestamoDTO = new ArrayList<>();
        for (TiposPrestamo tipoPrestamo : tiposPrestamo) {
            tiposPrestamoDTO.add(this.transformarADTO(tipoPrestamo));
        }
        return tiposPrestamoDTO;
    }

    public List<TiposPrestamoDTO> obtenerPorEstado(String estado) {
        log.info("Obteniendo tipos de préstamo por estado: {}", estado);
        List<TiposPrestamo> tiposPrestamo = this.tiposPrestamoRepository.findByEstado(estado);
        List<TiposPrestamoDTO> tiposPrestamoDTO = new ArrayList<>();
        for (TiposPrestamo tipoPrestamo : tiposPrestamo) {
            tiposPrestamoDTO.add(this.transformarADTO(tipoPrestamo));
        }
        return tiposPrestamoDTO;
    }

    public TiposPrestamoDTO obtenerPorId(Integer id) {
        log.info("Obteniendo tipo de préstamo por ID: {}", id);
        Optional<TiposPrestamo> tipoPrestamoOpt = this.tiposPrestamoRepository.findById(id);
        if (tipoPrestamoOpt.isPresent()) {
            return this.transformarADTO(tipoPrestamoOpt.get());
        } else {
            throw new EntityNotFoundException("Tipo de Préstamo", "No se encontró el tipo de préstamo con id: " + id);
        }
    }

    @Transactional
    public TiposPrestamoDTO crear(TiposPrestamoDTO tipoPrestamoDTO) {
        log.info("Creando nuevo tipo de préstamo: {}", tipoPrestamoDTO);
        try {
            // Validar que el tipo de cliente sea uno de los valores del enum
            boolean tipoClienteValido = false;
            for (TipoClienteEnum tipoEnum : TipoClienteEnum.values()) {
                if (tipoEnum.getValor().equals(tipoPrestamoDTO.getTipoCliente())) {
                    tipoClienteValido = true;
                    break;
                }
            }
            if (!tipoClienteValido) {
                throw new CreateException("Tipo de Préstamo",
                        "El tipo de cliente debe ser uno de los siguientes valores: PERSONA, EMPRESA, AMBOS");
            }

            TiposPrestamo tipoPrestamo = new TiposPrestamo();
            tipoPrestamo.setIdMoneda(tipoPrestamoDTO.getIdMoneda());
            tipoPrestamo.setNombre(tipoPrestamoDTO.getNombre());
            tipoPrestamo.setDescripcion(tipoPrestamoDTO.getDescripcion());
            tipoPrestamo.setRequisitos(tipoPrestamoDTO.getRequisitos());
            tipoPrestamo.setTipoCliente(tipoPrestamoDTO.getTipoCliente());
            tipoPrestamo.setFechaCreacion(Instant.now());
            tipoPrestamo.setFechaModificacion(Instant.now());
            tipoPrestamo.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            tipoPrestamo.setVersion(new BigDecimal(1));

            TiposPrestamo tipoPrestamoGuardado = this.tiposPrestamoRepository.save(tipoPrestamo);
            return this.transformarADTO(tipoPrestamoGuardado);
        } catch (Exception e) {
            log.error("Error al crear el tipo de préstamo", e);
            throw new CreateException("Tipo de Préstamo", "Error al crear el tipo de préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public TiposPrestamoDTO actualizar(Integer id, TiposPrestamoDTO tipoPrestamoDTO) {
        log.info("Actualizando tipo de préstamo con ID: {} con datos: {}", id, tipoPrestamoDTO);
        try {
            Optional<TiposPrestamo> tipoPrestamoOpt = this.tiposPrestamoRepository.findById(id);
            if (tipoPrestamoOpt.isPresent()) {
                TiposPrestamo tipoPrestamo = tipoPrestamoOpt.get();

                // Validar que el tipo de cliente sea uno de los valores del enum
                if (tipoPrestamoDTO.getTipoCliente() != null) {
                    boolean tipoClienteValido = false;
                    for (TipoClienteEnum tipoEnum : TipoClienteEnum.values()) {
                        if (tipoEnum.getValor().equals(tipoPrestamoDTO.getTipoCliente())) {
                            tipoClienteValido = true;
                            break;
                        }
                    }
                    if (!tipoClienteValido) {
                        throw new UpdateException("Tipo de Préstamo",
                                "El tipo de cliente debe ser uno de los siguientes valores: PERSONA, EMPRESA, AMBOS");
                    }
                }

                tipoPrestamo.setIdMoneda(tipoPrestamoDTO.getIdMoneda());
                tipoPrestamo.setNombre(tipoPrestamoDTO.getNombre());
                tipoPrestamo.setDescripcion(tipoPrestamoDTO.getDescripcion());
                tipoPrestamo.setRequisitos(tipoPrestamoDTO.getRequisitos());
                tipoPrestamo.setTipoCliente(tipoPrestamoDTO.getTipoCliente());
                tipoPrestamo.setFechaModificacion(Instant.now());

                if (tipoPrestamoDTO.getEstado() != null) {
                    tipoPrestamo.setEstado(tipoPrestamoDTO.getEstado());
                }

                BigDecimal newVersion = tipoPrestamo.getVersion().add(new BigDecimal(1));
                tipoPrestamo.setVersion(newVersion);

                TiposPrestamo tipoPrestamoActualizado = this.tiposPrestamoRepository.save(tipoPrestamo);
                return this.transformarADTO(tipoPrestamoActualizado);
            } else {
                throw new EntityNotFoundException("Tipo de Préstamo",
                        "No se encontró el tipo de préstamo con id: " + id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar el tipo de préstamo", e);
            throw new UpdateException("Tipo de Préstamo", "Error al actualizar el tipo de préstamo: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando tipo de préstamo con ID: {}", id);
        Optional<TiposPrestamo> tipoPrestamoOpt = this.tiposPrestamoRepository.findById(id);
        if (tipoPrestamoOpt.isPresent()) {
            TiposPrestamo tipoPrestamo = tipoPrestamoOpt.get();
            tipoPrestamo.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            this.tiposPrestamoRepository.save(tipoPrestamo);
        } else {
            throw new EntityNotFoundException("Tipo de Préstamo", "No se encontró el tipo de préstamo con id: " + id);
        }
    }

    private TiposPrestamoDTO transformarADTO(TiposPrestamo tipoPrestamo) {
        return TiposPrestamoDTO.builder()
                .id(tipoPrestamo.getId())
                .idMoneda(tipoPrestamo.getIdMoneda())
                .nombre(tipoPrestamo.getNombre())
                .descripcion(tipoPrestamo.getDescripcion())
                .requisitos(tipoPrestamo.getRequisitos())
                .tipoCliente(tipoPrestamo.getTipoCliente())
                .fechaCreacion(tipoPrestamo.getFechaCreacion())
                .fechaModificacion(tipoPrestamo.getFechaModificacion())
                .estado(tipoPrestamo.getEstado())
                .version(tipoPrestamo.getVersion())
                .build();
    }
}
