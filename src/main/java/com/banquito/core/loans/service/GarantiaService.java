package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.GarantiaDTO;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.enums.TipoGarantiaEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.Garantia;
import com.banquito.core.loans.repositorio.GarantiaRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GarantiaService {

    private final GarantiaRepositorio garantiaRepository;

    public GarantiaService(GarantiaRepositorio garantiaRepository) {
        this.garantiaRepository = garantiaRepository;
    }

    public List<GarantiaDTO> obtenerTodas() {
        log.info("Obteniendo todas las garantías");
        List<Garantia> garantias = this.garantiaRepository.findAll();
        List<GarantiaDTO> garantiasDTO = new ArrayList<>();
        for (Garantia garantia : garantias) {
            garantiasDTO.add(this.transformarADTO(garantia));
        }
        return garantiasDTO;
    }

    public GarantiaDTO obtenerPorId(Integer id) {
        log.info("Obteniendo garantía por ID: {}", id);
        Optional<Garantia> garantiaOpt = this.garantiaRepository.findById(id);
        if (garantiaOpt.isPresent()) {
            return this.transformarADTO(garantiaOpt.get());
        } else {
            throw new EntityNotFoundException("Garantía", "No se encontró la garantía con id: " + id);
        }
    }

    @Transactional
    public GarantiaDTO crear(GarantiaDTO garantiaDTO) {
        log.info("Creando nueva garantía: {}", garantiaDTO);
        try {
            // Validar que el tipo de garantía sea uno de los valores del enum
            boolean tipoValido = false;
            for (TipoGarantiaEnum tipoEnum : TipoGarantiaEnum.values()) {
                if (tipoEnum.getValor().equals(garantiaDTO.getTipoGarantia())) {
                    tipoValido = true;
                    break;
                }
            }
            if (!tipoValido) {
                throw new CreateException("Garantía",
                        "El tipo de garantía debe ser uno de los siguientes valores: HIPOTECA, PRENDARIA, PERSONAL");
            }

            Garantia garantia = new Garantia();
            garantia.setTipoGarantia(garantiaDTO.getTipoGarantia());
            garantia.setDescripcion(garantiaDTO.getDescripcion());
            garantia.setValor(garantiaDTO.getValor());
            garantia.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            garantia.setVersion(1L);

            Garantia garantiaGuardada = this.garantiaRepository.save(garantia);
            return this.transformarADTO(garantiaGuardada);
        } catch (Exception e) {
            log.error("Error al crear la garantía", e);
            throw new CreateException("Garantía", "Error al crear la garantía: " + e.getMessage());
        }
    }

    @Transactional
    public GarantiaDTO actualizar(Integer id, GarantiaDTO garantiaDTO) {
        log.info("Actualizando garantía con ID: {} con datos: {}", id, garantiaDTO);
        try {
            Optional<Garantia> garantiaOpt = this.garantiaRepository.findById(id);
            if (garantiaOpt.isPresent()) {
                Garantia garantia = garantiaOpt.get();

                // Validar que el tipo de garantía sea uno de los valores del enum
                if (garantiaDTO.getTipoGarantia() != null) {
                    boolean tipoValido = false;
                    for (TipoGarantiaEnum tipoEnum : TipoGarantiaEnum.values()) {
                        if (tipoEnum.getValor().equals(garantiaDTO.getTipoGarantia())) {
                            tipoValido = true;
                            break;
                        }
                    }
                    if (!tipoValido) {
                        throw new UpdateException("Garantía",
                                "El tipo de garantía debe ser uno de los siguientes valores: HIPOTECA, PRENDARIA, PERSONAL");
                    }
                }

                garantia.setTipoGarantia(garantiaDTO.getTipoGarantia());
                garantia.setDescripcion(garantiaDTO.getDescripcion());
                garantia.setValor(garantiaDTO.getValor());
                if (garantiaDTO.getEstado() != null) {
                    garantia.setEstado(garantiaDTO.getEstado());
                }

                Long newVersion = garantia.getVersion() + 1L;
                garantia.setVersion(newVersion);

                Garantia garantiaActualizada = this.garantiaRepository.save(garantia);
                return this.transformarADTO(garantiaActualizada);
            } else {
                throw new EntityNotFoundException("Garantía", "No se encontró la garantía con id: " + id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar la garantía", e);
            throw new UpdateException("Garantía", "Error al actualizar la garantía: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando garantía con ID: {}", id);
        Optional<Garantia> garantiaOpt = this.garantiaRepository.findById(id);
        if (garantiaOpt.isPresent()) {
            Garantia garantia = garantiaOpt.get();
            garantia.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
            this.garantiaRepository.save(garantia);
        } else {
            throw new EntityNotFoundException("Garantía", "No se encontró la garantía con id: " + id);
        }
    }

    private GarantiaDTO transformarADTO(Garantia garantia) {
        return GarantiaDTO.builder()
                .id(garantia.getId())
                .tipoGarantia(garantia.getTipoGarantia())
                .descripcion(garantia.getDescripcion())
                .valor(garantia.getValor())
                .estado(garantia.getEstado())
                .version(garantia.getVersion())
                .build();
    }
}
