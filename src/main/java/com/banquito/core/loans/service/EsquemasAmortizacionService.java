package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.EsquemasAmortizacionDTO;
import com.banquito.core.loans.enums.EsquemaAmortizacionEnum;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.EsquemasAmortizacion;
import com.banquito.core.loans.modelo.TiposPrestamo;
import com.banquito.core.loans.repositorio.EsquemasAmortizacionRepository;
import com.banquito.core.loans.repositorio.TiposPrestamoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EsquemasAmortizacionService {

    private final EsquemasAmortizacionRepository esquemaRepository;
    private final TiposPrestamoRepository tiposPrestamoRepository;

    public EsquemasAmortizacionService(EsquemasAmortizacionRepository esquemaRepository,
            TiposPrestamoRepository tiposPrestamoRepository) {
        this.esquemaRepository = esquemaRepository;
        this.tiposPrestamoRepository = tiposPrestamoRepository;
    }

    public List<EsquemasAmortizacionDTO> obtenerTodos() {
        log.info("Obteniendo todos los esquemas de amortización");
        List<EsquemasAmortizacion> esquemas = this.esquemaRepository.findAll();
        List<EsquemasAmortizacionDTO> esquemasDTO = new ArrayList<>();
        for (EsquemasAmortizacion esquema : esquemas) {
            esquemasDTO.add(this.transformarADTO(esquema));
        }
        return esquemasDTO;
    }

    public List<EsquemasAmortizacionDTO> obtenerPorEstado(String estado) {
        log.info("Obteniendo esquemas de amortización por estado: {}", estado);
        List<EsquemasAmortizacion> esquemas = this.esquemaRepository.findByEstado(estado);
        List<EsquemasAmortizacionDTO> esquemasDTO = new ArrayList<>();
        for (EsquemasAmortizacion esquema : esquemas) {
            esquemasDTO.add(this.transformarADTO(esquema));
        }
        return esquemasDTO;
    }

    public EsquemasAmortizacionDTO obtenerPorId(Integer id) {
        log.info("Obteniendo esquema de amortización por ID: {}", id);
        Optional<EsquemasAmortizacion> esquemaOpt = this.esquemaRepository.findById(id);
        if (esquemaOpt.isPresent()) {
            return this.transformarADTO(esquemaOpt.get());
        } else {
            throw new EntityNotFoundException("Esquema de Amortización", "No se encontró el esquema con id: " + id);
        }
    }

    @Transactional
    public EsquemasAmortizacionDTO crear(EsquemasAmortizacionDTO esquemaDTO) {
        log.info("Creando nuevo esquema de amortización: {}", esquemaDTO);
        try {
            Optional<EsquemasAmortizacion> esquemaExistente = this.esquemaRepository
                    .findByNombre(esquemaDTO.getNombre());
            if (esquemaExistente.isPresent()) {
                throw new CreateException("Esquema de Amortización",
                        "Ya existe un esquema con el nombre: " + esquemaDTO.getNombre());
            }

            // Validar que el nombre del esquema de amortización sea uno de los valores del
            // enum
            boolean nombreValido = false;
            for (EsquemaAmortizacionEnum esquemaEnum : EsquemaAmortizacionEnum.values()) {
                if (esquemaEnum.getValor().equals(esquemaDTO.getNombre())) {
                    nombreValido = true;
                    break;
                }
            }
            if (!nombreValido) {
                throw new CreateException("Esquema de Amortización",
                        "El nombre del esquema debe ser uno de los siguientes valores: FRANCES, AMERICANO, ALEMAN");
            }

            Optional<TiposPrestamo> tipoPrestamo = this.tiposPrestamoRepository
                    .findById(esquemaDTO.getIdTipoPrestamo());
            if (!tipoPrestamo.isPresent()) {
                throw new CreateException("Esquema de Amortización",
                        "No existe el tipo de préstamo con id: " + esquemaDTO.getIdTipoPrestamo());
            }

            EsquemasAmortizacion esquema = new EsquemasAmortizacion();
            esquema.setIdTipoPrestamo(tipoPrestamo.get());
            esquema.setNombre(esquemaDTO.getNombre());
            esquema.setDescripcion(esquemaDTO.getDescripcion());
            esquema.setPermiteGracia(esquemaDTO.getPermiteGracia());
            esquema.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            esquema.setVersion(new BigDecimal(1));

            EsquemasAmortizacion esquemaGuardado = this.esquemaRepository.save(esquema);
            return this.transformarADTO(esquemaGuardado);
        } catch (Exception e) {
            log.error("Error al crear el esquema de amortización", e);
            throw new CreateException("Esquema de Amortización", "Error al crear el esquema: " + e.getMessage());
        }
    }

    @Transactional
    public EsquemasAmortizacionDTO actualizar(Integer id, EsquemasAmortizacionDTO esquemaDTO) {
        log.info("Actualizando esquema de amortización con ID: {} con datos: {}", id, esquemaDTO);
        try {
            Optional<EsquemasAmortizacion> esquemaOpt = this.esquemaRepository.findById(id);
            if (esquemaOpt.isPresent()) {
                EsquemasAmortizacion esquema = esquemaOpt.get();
                if (!esquema.getNombre().equals(esquemaDTO.getNombre())) {
                    Optional<EsquemasAmortizacion> esquemaExistente = this.esquemaRepository
                            .findByNombre(esquemaDTO.getNombre());
                    if (esquemaExistente.isPresent() && !esquemaExistente.get().getId().equals(id)) {
                        throw new UpdateException("Esquema de Amortización",
                                "Ya existe un esquema con el nombre: " + esquemaDTO.getNombre());
                    }

                    // Validar que el nombre del esquema de amortización sea uno de los valores del
                    // enum
                    boolean nombreValido = false;
                    for (EsquemaAmortizacionEnum esquemaEnum : EsquemaAmortizacionEnum.values()) {
                        if (esquemaEnum.getValor().equals(esquemaDTO.getNombre())) {
                            nombreValido = true;
                            break;
                        }
                    }
                    if (!nombreValido) {
                        throw new UpdateException("Esquema de Amortización",
                                "El nombre del esquema debe ser uno de los siguientes valores: FRANCES, AMERICANO, ALEMAN");
                    }
                }

                if (!esquema.getIdTipoPrestamo().getId().equals(esquemaDTO.getIdTipoPrestamo())) {
                    Optional<TiposPrestamo> tipoPrestamo = this.tiposPrestamoRepository
                            .findById(esquemaDTO.getIdTipoPrestamo());
                    if (!tipoPrestamo.isPresent()) {
                        throw new UpdateException("Esquema de Amortización",
                                "No existe el tipo de préstamo con id: " + esquemaDTO.getIdTipoPrestamo());
                    }
                    esquema.setIdTipoPrestamo(tipoPrestamo.get());
                }

                esquema.setNombre(esquemaDTO.getNombre());
                esquema.setDescripcion(esquemaDTO.getDescripcion());
                esquema.setPermiteGracia(esquemaDTO.getPermiteGracia());

                if (esquemaDTO.getEstado() != null) {
                    esquema.setEstado(esquemaDTO.getEstado());
                }

                BigDecimal newVersion = esquema.getVersion().add(new BigDecimal(1));
                esquema.setVersion(newVersion);

                EsquemasAmortizacion esquemaActualizado = this.esquemaRepository.save(esquema);
                return this.transformarADTO(esquemaActualizado);
            } else {
                throw new EntityNotFoundException("Esquema de Amortización", "No se encontró el esquema con id: " + id);
            }
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar el esquema de amortización", e);
            throw new UpdateException("Esquema de Amortización", "Error al actualizar el esquema: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando esquema de amortización con ID: {}", id);
        Optional<EsquemasAmortizacion> esquemaOpt = this.esquemaRepository.findById(id);
        if (esquemaOpt.isPresent()) {
            EsquemasAmortizacion esquema = esquemaOpt.get();
            esquema.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            this.esquemaRepository.save(esquema);
        } else {
            throw new EntityNotFoundException("Esquema de Amortización", "No se encontró el esquema con id: " + id);
        }
    }

    private EsquemasAmortizacionDTO transformarADTO(EsquemasAmortizacion esquema) {
        return EsquemasAmortizacionDTO.builder()
                .id(esquema.getId())
                .idTipoPrestamo(esquema.getIdTipoPrestamo() != null ? esquema.getIdTipoPrestamo().getId() : null)
                .nombre(esquema.getNombre())
                .descripcion(esquema.getDescripcion())
                .permiteGracia(esquema.getPermiteGracia())
                .estado(esquema.getEstado())
                .version(esquema.getVersion())
                .build();
    }
}
