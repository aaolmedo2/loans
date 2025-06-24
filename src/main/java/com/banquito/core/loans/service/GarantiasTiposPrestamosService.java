package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.GarantiasTiposPrestamoDTO;
import com.banquito.core.loans.DTO.GarantiasTiposPrestamosClienteDTO;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.Garantia;
import com.banquito.core.loans.modelo.GarantiasTiposPrestamo;
import com.banquito.core.loans.modelo.GarantiasTiposPrestamosCliente;
import com.banquito.core.loans.modelo.PrestamosCliente;
import com.banquito.core.loans.modelo.TiposPrestamo;
import com.banquito.core.loans.repositorio.GarantiaRepositorio;
import com.banquito.core.loans.repositorio.GarantiasTiposPrestamoRepositorio;
import com.banquito.core.loans.repositorio.GarantiasTiposPrestamosClienteRepositorio;
import com.banquito.core.loans.repositorio.PrestamosClienteRepositorio;
import com.banquito.core.loans.repositorio.TiposPrestamoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GarantiasTiposPrestamosService {
    private final GarantiasTiposPrestamoRepositorio garantiasTiposPrestamoRepositorio;
    private final GarantiasTiposPrestamosClienteRepositorio garantiasTiposPrestamosClienteRepositorio;
    private final GarantiaRepositorio garantiaRepository;
    private final TiposPrestamoRepositorio tiposPrestamoRepository;
    private final PrestamosClienteRepositorio prestamosClienteRepository;

    public GarantiasTiposPrestamosService(
            GarantiasTiposPrestamoRepositorio garantiasTiposPrestamoRepositorio,
            GarantiasTiposPrestamosClienteRepositorio garantiasTiposPrestamosClienteRepositorio,
            GarantiaRepositorio garantiaRepository,
            TiposPrestamoRepositorio tiposPrestamoRepository,
            PrestamosClienteRepositorio prestamosClienteRepository) {
        this.garantiasTiposPrestamoRepositorio = garantiasTiposPrestamoRepositorio;
        this.garantiasTiposPrestamosClienteRepositorio = garantiasTiposPrestamosClienteRepositorio;
        this.garantiaRepository = garantiaRepository;
        this.tiposPrestamoRepository = tiposPrestamoRepository;
        this.prestamosClienteRepository = prestamosClienteRepository;
    }

    // CRUD GarantiasTiposPrestamo
    public List<GarantiasTiposPrestamoDTO> obtenerTodosTipos() {
        log.info("Obteniendo todas las relaciones garantía-tipo préstamo");
        List<GarantiasTiposPrestamo> lista = this.garantiasTiposPrestamoRepositorio.findAll();
        List<GarantiasTiposPrestamoDTO> dtos = new ArrayList<>();
        for (GarantiasTiposPrestamo item : lista) {
            dtos.add(this.transformarADTO(item));
        }
        return dtos;
    }

    public GarantiasTiposPrestamoDTO obtenerTipoPorId(Integer id) {
        log.info("Obteniendo relación garantía-tipo préstamo por ID: {}", id);
        Optional<GarantiasTiposPrestamo> opt = this.garantiasTiposPrestamoRepositorio.findById(id);
        if (opt.isPresent()) {
            return this.transformarADTO(opt.get());
        } else {
            throw new EntityNotFoundException("Garantía-Tipo Préstamo", "No se encontró la relación con id: " + id);
        }
    }

    @Transactional
    public GarantiasTiposPrestamoDTO crearTipo(GarantiasTiposPrestamoDTO dto) {
        log.info("Creando nueva relación garantía-tipo préstamo: {}", dto);
        try {
            Garantia garantia = garantiaRepository.findById(dto.getIdGarantia())
                    .orElseThrow(() -> new CreateException("Garantía-Tipo Préstamo",
                            "No existe la garantía con id: " + dto.getIdGarantia()));
            TiposPrestamo tipoPrestamo = tiposPrestamoRepository.findById(dto.getIdTipoPrestamo())
                    .orElseThrow(() -> new CreateException("Garantía-Tipo Préstamo",
                            "No existe el tipo de préstamo con id: " + dto.getIdTipoPrestamo()));
            GarantiasTiposPrestamo entity = new GarantiasTiposPrestamo();
            entity.setIdGarantia(garantia);
            entity.setIdTipoPrestamo(tipoPrestamo);
            entity.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            // entity.setEstado(dto.getEstado());
            entity.setVersion(1L);
            GarantiasTiposPrestamo guardado = garantiasTiposPrestamoRepositorio.save(entity);
            return this.transformarADTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear la relación garantía-tipo préstamo", e);
            throw new CreateException("Garantía-Tipo Préstamo", "Error al crear: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarTipo(Integer id) {
        log.info("Eliminando (soft) relación garantía-tipo préstamo con ID: {}", id);
        GarantiasTiposPrestamo entity = garantiasTiposPrestamoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garantía-Tipo Préstamo",
                        "No se encontró la relación con id: " + id));
        // entity.setEstado("INACTIVO");
        entity.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
        garantiasTiposPrestamoRepositorio.save(entity);
    }

    // CRUD GarantiasTiposPrestamosCliente
    public List<GarantiasTiposPrestamosClienteDTO> obtenerTodosClientes() {
        log.info("Obteniendo todas las relaciones garantía-tipo préstamo-cliente");
        List<GarantiasTiposPrestamosCliente> lista = this.garantiasTiposPrestamosClienteRepositorio.findAll();
        List<GarantiasTiposPrestamosClienteDTO> dtos = new ArrayList<>();
        for (GarantiasTiposPrestamosCliente item : lista) {
            dtos.add(this.transformarClienteADTO(item));
        }
        return dtos;
    }

    public GarantiasTiposPrestamosClienteDTO obtenerClientePorId(Integer id) {
        log.info("Obteniendo relación garantía-tipo préstamo-cliente por ID: {}", id);
        Optional<GarantiasTiposPrestamosCliente> opt = this.garantiasTiposPrestamosClienteRepositorio.findById(id);
        if (opt.isPresent()) {
            return this.transformarClienteADTO(opt.get());
        } else {
            throw new EntityNotFoundException("Garantía-Tipo Préstamo-Cliente",
                    "No se encontró la relación con id: " + id);
        }
    }

    @Transactional
    public GarantiasTiposPrestamosClienteDTO crearCliente(GarantiasTiposPrestamosClienteDTO dto) {
        log.info("Creando nueva relación garantía-tipo préstamo-cliente: {}", dto);
        try {
            PrestamosCliente prestamoCliente = prestamosClienteRepository.findById(dto.getIdPrestamoCliente())
                    .orElseThrow(() -> new CreateException("Garantía-Tipo Préstamo-Cliente",
                            "No existe el préstamo cliente con id: " + dto.getIdPrestamoCliente()));
            GarantiasTiposPrestamo garantiaTipo = garantiasTiposPrestamoRepositorio
                    .findById(dto.getIdGarantiaTipoPrestamo())
                    .orElseThrow(() -> new CreateException("Garantía-Tipo Préstamo-Cliente",
                            "No existe la relación garantía-tipo préstamo con id: " + dto.getIdGarantiaTipoPrestamo()));
            GarantiasTiposPrestamosCliente entity = new GarantiasTiposPrestamosCliente();
            entity.setIdPrestamoCliente(prestamoCliente);
            entity.setIdGarantiaTipoPrestamo(garantiaTipo);
            entity.setMontoTasado(dto.getMontoTasado());
            entity.setFechaRegistro(dto.getFechaRegistro());
            entity.setDescripcion(dto.getDescripcion());
            entity.setDocumentoReferencia(dto.getDocumentoReferencia());
            // entity.setEstado(dto.getEstado());
            entity.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            entity.setVersion(1L);
            GarantiasTiposPrestamosCliente guardado = garantiasTiposPrestamosClienteRepositorio.save(entity);
            return this.transformarClienteADTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear la relación garantía-tipo préstamo-cliente", e);
            throw new CreateException("Garantía-Tipo Préstamo-Cliente", "Error al crear: " + e.getMessage());
        }
    }

    @Transactional
    public GarantiasTiposPrestamosClienteDTO actualizarCliente(Integer id, GarantiasTiposPrestamosClienteDTO dto) {
        log.info("Actualizando relación garantía-tipo préstamo-cliente con ID: {}", id);
        try {
            GarantiasTiposPrestamosCliente entity = garantiasTiposPrestamosClienteRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Garantía-Tipo Préstamo-Cliente",
                            "No se encontró la relación con id: " + id));
            if (!entity.getIdPrestamoCliente().getId().equals(dto.getIdPrestamoCliente())) {
                PrestamosCliente prestamoCliente = prestamosClienteRepository.findById(dto.getIdPrestamoCliente())
                        .orElseThrow(() -> new UpdateException("Garantía-Tipo Préstamo-Cliente",
                                "No existe el préstamo cliente con id: " + dto.getIdPrestamoCliente()));
                entity.setIdPrestamoCliente(prestamoCliente);
            }
            if (!entity.getIdGarantiaTipoPrestamo().getId().equals(dto.getIdGarantiaTipoPrestamo())) {
                GarantiasTiposPrestamo garantiaTipo = garantiasTiposPrestamoRepositorio
                        .findById(dto.getIdGarantiaTipoPrestamo())
                        .orElseThrow(() -> new UpdateException("Garantía-Tipo Préstamo-Cliente",
                                "No existe la relación garantía-tipo préstamo con id: "
                                        + dto.getIdGarantiaTipoPrestamo()));
                entity.setIdGarantiaTipoPrestamo(garantiaTipo);
            }
            entity.setMontoTasado(dto.getMontoTasado());
            entity.setFechaRegistro(dto.getFechaRegistro());
            entity.setDescripcion(dto.getDescripcion());
            entity.setDocumentoReferencia(dto.getDocumentoReferencia());
            if (dto.getEstado() != null) {
                entity.setEstado(dto.getEstado());
            }
            entity.setVersion(entity.getVersion() + 1L);
            GarantiasTiposPrestamosCliente actualizado = garantiasTiposPrestamosClienteRepositorio.save(entity);
            return this.transformarClienteADTO(actualizado);
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar la relación garantía-tipo préstamo-cliente", e);
            throw new UpdateException("Garantía-Tipo Préstamo-Cliente", "Error al actualizar: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarCliente(Integer id) {
        log.info("Eliminando (soft) relación garantía-tipo préstamo-cliente con ID: {}", id);
        GarantiasTiposPrestamosCliente entity = garantiasTiposPrestamosClienteRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Garantía-Tipo Préstamo-Cliente",
                        "No se encontró la relación con id: " + id));
        // entity.setEstado("INACTIVO");
        entity.setEstado(EstadoGeneralEnum.INACTIVO.getValor());
        garantiasTiposPrestamosClienteRepositorio.save(entity);
    }

    // Transformadores
    private GarantiasTiposPrestamoDTO transformarADTO(GarantiasTiposPrestamo entity) {
        return GarantiasTiposPrestamoDTO.builder()
                .id(entity.getId())
                .idGarantia(entity.getIdGarantia().getId())
                .idTipoPrestamo(entity.getIdTipoPrestamo().getId())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }

    private GarantiasTiposPrestamosClienteDTO transformarClienteADTO(GarantiasTiposPrestamosCliente entity) {
        return GarantiasTiposPrestamosClienteDTO.builder()
                .id(entity.getId())
                .idPrestamoCliente(entity.getIdPrestamoCliente().getId())
                .idGarantiaTipoPrestamo(entity.getIdGarantiaTipoPrestamo().getId())
                .montoTasado(entity.getMontoTasado())
                .fechaRegistro(entity.getFechaRegistro())
                .descripcion(entity.getDescripcion())
                .documentoReferencia(entity.getDocumentoReferencia())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}