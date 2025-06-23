package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.SeguroDTO;
import com.banquito.core.loans.enums.EstadoSeguroEnum;
import com.banquito.core.loans.enums.TipoSeguroEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.Seguro;
import com.banquito.core.loans.repositorio.SeguroRepositorio;
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
public class SeguroService {

    private final SeguroRepositorio seguroRepository;

    public SeguroService(SeguroRepositorio seguroRepository) {
        this.seguroRepository = seguroRepository;
    }

    public List<SeguroDTO> obtenerTodos() {
        log.info("Obteniendo todos los seguros (primeros 20, ordenados alfabéticamente por compañía)");
        Pageable pageable = PageRequest.of(0, 20);
        List<Seguro> seguros = this.seguroRepository.findAllByOrderByCompaniaAsc(pageable);
        List<SeguroDTO> segurosDTO = new ArrayList<>();
        for (Seguro seguro : seguros) {
            segurosDTO.add(this.transformarADTO(seguro));
        }
        return segurosDTO;
    }

    public SeguroDTO obtenerPorId(Integer id) {
        log.info("Obteniendo seguro por ID: {}", id);
        Optional<Seguro> seguroOpt = this.seguroRepository.findById(id);
        if (seguroOpt.isPresent()) {
            return this.transformarADTO(seguroOpt.get());
        } else {
            throw new EntityNotFoundException("Seguro", "No se encontró el seguro con id: " + id);
        }
    }

    @Transactional
    public SeguroDTO crear(SeguroDTO seguroDTO) {
        log.info("Creando nuevo seguro: {}", seguroDTO);
        try {
            // Validar que el tipo de seguro sea uno de los valores del enum
            boolean tipoValido = false;
            for (TipoSeguroEnum tipoEnum : TipoSeguroEnum.values()) {
                if (tipoEnum.getValor().equals(seguroDTO.getTipoSeguro())) {
                    tipoValido = true;
                    break;
                }
            }
            if (!tipoValido) {
                throw new CreateException("Seguro",
                        "El tipo de seguro debe ser uno de los siguientes valores: VIDA, DESEMPLEO, PROTECCION_PAGOS, DESGRAVAMEN, INCENDIOS");
            }

            Seguro seguro = new Seguro();
            seguro.setTipoSeguro(seguroDTO.getTipoSeguro());
            seguro.setCompania(seguroDTO.getCompania());
            seguro.setMontoAsegurado(seguroDTO.getMontoAsegurado());
            seguro.setFechaInicio(seguroDTO.getFechaInicio());
            seguro.setFechaFin(seguroDTO.getFechaFin());
            seguro.setEstado(EstadoSeguroEnum.ACTIVO.name());
            seguro.setVersion(1L);

            Seguro seguroGuardado = this.seguroRepository.save(seguro);
            return this.transformarADTO(seguroGuardado);
        } catch (Exception e) {
            log.error("Error al crear el seguro", e);
            throw new CreateException("Seguro", "Error al crear el seguro: " + e.getMessage());
        }
    }

    @Transactional
    public SeguroDTO actualizar(Integer id, SeguroDTO seguroDTO) {
        log.info("Actualizando monto asegurado del seguro con ID: {} con valor: {}", id, seguroDTO.getMontoAsegurado());
        try {
            Optional<Seguro> seguroOpt = this.seguroRepository.findById(id);
            if (seguroOpt.isPresent()) {
                Seguro seguro = seguroOpt.get();

                // Solo permitimos actualizar el monto asegurado
                seguro.setMontoAsegurado(seguroDTO.getMontoAsegurado());

                Long newVersion = seguro.getVersion() + 1L;
                seguro.setVersion(newVersion);

                Seguro seguroActualizado = this.seguroRepository.save(seguro);
                return this.transformarADTO(seguroActualizado);
            } else {
                throw new EntityNotFoundException("Seguro", "No se encontró el seguro con id: " + id);
            }
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar el seguro", e);
            throw new UpdateException("Seguro", "Error al actualizar el seguro: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminar(Integer id) {
        log.info("Eliminando seguro con ID: {}", id);
        Optional<Seguro> seguroOpt = this.seguroRepository.findById(id);
        if (seguroOpt.isPresent()) {
            Seguro seguro = seguroOpt.get();
            seguro.setEstado(EstadoSeguroEnum.INACTIVO.name());
            this.seguroRepository.save(seguro);
        } else {
            throw new EntityNotFoundException("Seguro", "No se encontró el seguro con id: " + id);
        }
    }

    private SeguroDTO transformarADTO(Seguro seguro) {
        return SeguroDTO.builder()
                .id(seguro.getId())
                .tipoSeguro(seguro.getTipoSeguro())
                .compania(seguro.getCompania())
                .montoAsegurado(seguro.getMontoAsegurado())
                .fechaInicio(seguro.getFechaInicio())
                .fechaFin(seguro.getFechaFin())
                .estado(seguro.getEstado())
                .version(seguro.getVersion())
                .build();
    }
}
