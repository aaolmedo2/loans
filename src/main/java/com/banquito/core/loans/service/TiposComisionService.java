package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.TiposComisionDTO;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.enums.TipoCalculoComisionEnum;
import com.banquito.core.loans.enums.TipoComisionEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.TiposComisione;
import com.banquito.core.loans.repositorio.TiposComisioneRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TiposComisionService {

    private final TiposComisioneRepositorio tipoComisionRepository;

    public TiposComisionService(TiposComisioneRepositorio tipoComisionRepository) {
        this.tipoComisionRepository = tipoComisionRepository;
    }

    public List<TiposComisionDTO> obtenerTodos() {
        log.info("Obteniendo todos los tipos de comisión (primeros 20, ordenados alfabéticamente por nombre)");
        Pageable pageable = PageRequest.of(0, 20);
        List<TiposComisione> tiposComisiones = this.tipoComisionRepository.findAllByOrderByNombreAsc(pageable);
        List<TiposComisionDTO> tiposComisionesDTO = new ArrayList<>();
        for (TiposComisione tipoComision : tiposComisiones) {
            tiposComisionesDTO.add(this.transformarADTO(tipoComision));
        }
        return tiposComisionesDTO;
    }

    public TiposComisionDTO obtenerPorId(Integer id) {
        log.info("Obteniendo tipo de comisión por ID: {}", id);
        Optional<TiposComisione> tipoComisionOpt = this.tipoComisionRepository.findById(id);
        if (tipoComisionOpt.isPresent()) {
            return this.transformarADTO(tipoComisionOpt.get());
        } else {
            throw new EntityNotFoundException("Tipo de Comisión", "No se encontró el tipo de comisión con id: " + id);
        }
    }

    @Transactional
    public TiposComisionDTO crear(TiposComisionDTO tipoComisionDTO) {
        log.info("Creando nuevo tipo de comisión: {}", tipoComisionDTO);
        try {
            // Validar que el tipo de comisión sea uno de los valores del enum
            boolean tipoValido = false;
            for (TipoComisionEnum tipoEnum : TipoComisionEnum.values()) {
                if (tipoEnum.getValor().equals(tipoComisionDTO.getTipo())) {
                    tipoValido = true;
                    break;
                }
            }
            if (!tipoValido) {
                throw new CreateException("Tipo de Comisión",
                        "El tipo de comisión debe ser uno de los siguientes valores: ORIGINACION, PAGO ATRASADO, PREPAGO, MODIFICACION, SERVICIO ADICIONAL");
            }

            // Validar que el tipo de cálculo sea uno de los valores del enum
            boolean tipoCalculoValido = false;
            for (TipoCalculoComisionEnum tipoCalculoEnum : TipoCalculoComisionEnum.values()) {
                if (tipoCalculoEnum.getValor().equals(tipoComisionDTO.getTipoCalculo())) {
                    tipoCalculoValido = true;
                    break;
                }
            }
            if (!tipoCalculoValido) {
                throw new CreateException("Tipo de Comisión",
                        "El tipo de cálculo debe ser uno de los siguientes valores: PORCENTAJE, FIJO");
            }

            TiposComisione tipoComision = new TiposComisione();
            tipoComision.setTipo(tipoComisionDTO.getTipo());
            tipoComision.setNombre(tipoComisionDTO.getNombre());
            tipoComision.setDescripcion(tipoComisionDTO.getDescripcion());
            tipoComision.setTipoCalculo(tipoComisionDTO.getTipoCalculo());
            tipoComision.setMonto(tipoComisionDTO.getMonto());
            tipoComision.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            tipoComision.setVersion(1L);

            TiposComisione tipoComisionGuardado = this.tipoComisionRepository.save(tipoComision);
            return this.transformarADTO(tipoComisionGuardado);
        } catch (Exception e) {
            log.error("Error al crear el tipo de comisión", e);
            throw new CreateException("Tipo de Comisión", "Error al crear el tipo de comisión: " + e.getMessage());
        }
    }

    @Transactional
    public TiposComisionDTO actualizar(Integer id, TiposComisionDTO tipoComisionDTO) {
        log.info("Actualizando monto del tipo de comisión con ID: {} con valor: {}", id, tipoComisionDTO.getMonto());
        try {
            Optional<TiposComisione> tipoComisionOpt = this.tipoComisionRepository.findById(id);
            if (tipoComisionOpt.isPresent()) {
                TiposComisione tipoComision = tipoComisionOpt.get();

                // Solo permitimos actualizar el monto
                tipoComision.setMonto(tipoComisionDTO.getMonto());

                Long newVersion = tipoComision.getVersion() + 1L;
                tipoComision.setVersion(newVersion);

                TiposComisione tipoComisionActualizado = this.tipoComisionRepository.save(tipoComision);
                return this.transformarADTO(tipoComisionActualizado);
            } else {
                throw new EntityNotFoundException("Tipo de Comisión",
                        "No se encontró el tipo de comisión con id: " + id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar el tipo de comisión", e);
            throw new UpdateException("Tipo de Comisión", "Error al actualizar el tipo de comisión: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando tipo de comisión con ID: {}", id);
        Optional<TiposComisione> tipoComisionOpt = this.tipoComisionRepository.findById(id);
        if (tipoComisionOpt.isPresent()) {
            TiposComisione tipoComision = tipoComisionOpt.get();
            tipoComision.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            this.tipoComisionRepository.save(tipoComision);
        } else {
            throw new EntityNotFoundException("Tipo de Comisión", "No se encontró el tipo de comisión con id: " + id);
        }
    }

    private TiposComisionDTO transformarADTO(TiposComisione tipoComision) {
        return TiposComisionDTO.builder()
                .id(tipoComision.getId())
                .tipo(tipoComision.getTipo())
                .nombre(tipoComision.getNombre())
                .descripcion(tipoComision.getDescripcion())
                .tipoCalculo(tipoComision.getTipoCalculo())
                .monto(tipoComision.getMonto())
                .estado(tipoComision.getEstado())
                .version(tipoComision.getVersion())
                .build();
    }
}
