package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.SegurosPrestamoDTO;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.DTO.SegurosPrestamoClienteDTO;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.exception.UpdateException;
import com.banquito.core.loans.modelo.PrestamosCliente;
import com.banquito.core.loans.modelo.Seguro;
import com.banquito.core.loans.modelo.SegurosPrestamo;
import com.banquito.core.loans.modelo.SegurosPrestamoCliente;
import com.banquito.core.loans.modelo.Prestamo;
import com.banquito.core.loans.repositorio.PrestamosClienteRepositorio;
import com.banquito.core.loans.repositorio.SeguroRepositorio;
import com.banquito.core.loans.repositorio.SegurosPrestamoRepositorio;
import com.banquito.core.loans.repositorio.SegurosPrestamoClienteRepositorio;
import com.banquito.core.loans.repositorio.PrestamoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SegurosPrestamosService {
    private final SegurosPrestamoRepositorio segurosPrestamoRepositorio;
    private final SegurosPrestamoClienteRepositorio segurosPrestamoClienteRepositorio;
    private final SeguroRepositorio seguroRepository;
    private final PrestamoRepositorio prestamoRepository;
    private final PrestamosClienteRepositorio prestamosClienteRepository;

    public SegurosPrestamosService(
            SegurosPrestamoRepositorio segurosPrestamoRepositorio,
            SegurosPrestamoClienteRepositorio segurosPrestamoClienteRepositorio,
            SeguroRepositorio seguroRepository,
            PrestamoRepositorio prestamoRepository,
            PrestamosClienteRepositorio prestamosClienteRepository) {
        this.segurosPrestamoRepositorio = segurosPrestamoRepositorio;
        this.segurosPrestamoClienteRepositorio = segurosPrestamoClienteRepositorio;
        this.seguroRepository = seguroRepository;
        this.prestamoRepository = prestamoRepository;
        this.prestamosClienteRepository = prestamosClienteRepository;
    }

    // CRUD SegurosPrestamo
    public List<SegurosPrestamoDTO> obtenerTodosSegurosPrestamo() {
        log.info("Obteniendo todas las relaciones seguro-préstamo");
        List<SegurosPrestamo> lista = this.segurosPrestamoRepositorio.findAll();
        List<SegurosPrestamoDTO> dtos = new ArrayList<>();
        for (SegurosPrestamo item : lista) {
            dtos.add(this.transformarADTO(item));
        }
        return dtos;
    }

    public SegurosPrestamoDTO obtenerSeguroPrestamoPorId(Integer id) {
        log.info("Obteniendo relación seguro-préstamo por ID: {}", id);
        Optional<SegurosPrestamo> opt = this.segurosPrestamoRepositorio.findById(id);
        if (opt.isPresent()) {
            return this.transformarADTO(opt.get());
        } else {
            throw new EntityNotFoundException("Seguro-Préstamo", "No se encontró la relación con id: " + id);
        }
    }

    @Transactional
    public SegurosPrestamoDTO crearSeguroPrestamo(SegurosPrestamoDTO dto) {
        log.info("Creando nueva relación seguro-préstamo: {}", dto);
        try {
            Seguro seguro = seguroRepository.findById(dto.getIdSeguro())
                    .orElseThrow(() -> new CreateException("Seguro-Préstamo",
                            "No existe el seguro con id: " + dto.getIdSeguro()));
            Prestamo prestamo = prestamoRepository.findById(dto.getIdPrestamo())
                    .orElseThrow(() -> new CreateException("Seguro-Préstamo",
                            "No existe el préstamo con id: " + dto.getIdPrestamo()));
            SegurosPrestamo entity = new SegurosPrestamo();
            entity.setIdSeguro(seguro);
            entity.setIdPrestamo(prestamo);
            // entity.setEstado(dto.getEstado());
            entity.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            entity.setVersion(1L);
            SegurosPrestamo guardado = segurosPrestamoRepositorio.save(entity);
            return this.transformarADTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear la relación seguro-préstamo", e);
            throw new CreateException("Seguro-Préstamo", "Error al crear: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarSeguroPrestamo(Integer id) {
        log.info("Eliminando (soft) relación seguro-préstamo con ID: {}", id);
        SegurosPrestamo entity = segurosPrestamoRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seguro-Préstamo",
                        "No se encontró la relación con id: " + id));
        entity.setEstado("INACTIVO");
        segurosPrestamoRepositorio.save(entity);
    }

    // CRUD SegurosPrestamoCliente
    public List<SegurosPrestamoClienteDTO> obtenerTodosSegurosPrestamoCliente() {
        log.info("Obteniendo todas las relaciones seguro-préstamo-cliente");
        List<SegurosPrestamoCliente> lista = this.segurosPrestamoClienteRepositorio.findAll();
        List<SegurosPrestamoClienteDTO> dtos = new ArrayList<>();
        for (SegurosPrestamoCliente item : lista) {
            dtos.add(this.transformarClienteADTO(item));
        }
        return dtos;
    }

    public SegurosPrestamoClienteDTO obtenerSeguroPrestamoClientePorId(Integer id) {
        log.info("Obteniendo relación seguro-préstamo-cliente por ID: {}", id);
        Optional<SegurosPrestamoCliente> opt = this.segurosPrestamoClienteRepositorio.findById(id);
        if (opt.isPresent()) {
            return this.transformarClienteADTO(opt.get());
        } else {
            throw new EntityNotFoundException("Seguro-Préstamo-Cliente", "No se encontró la relación con id: " + id);
        }
    }

    @Transactional
    public SegurosPrestamoClienteDTO crearSeguroPrestamoCliente(SegurosPrestamoClienteDTO dto) {
        log.info("Creando nueva relación seguro-préstamo-cliente: {}", dto);
        try {
            PrestamosCliente prestamoCliente = prestamosClienteRepository.findById(dto.getIdPrestamoCliente())
                    .orElseThrow(() -> new CreateException("Seguro-Préstamo-Cliente",
                            "No existe el préstamo cliente con id: " + dto.getIdPrestamoCliente()));
            SegurosPrestamo seguroPrestamo = segurosPrestamoRepositorio.findById(dto.getIdSeguroPrestamo())
                    .orElseThrow(() -> new CreateException("Seguro-Préstamo-Cliente",
                            "No existe la relación seguro-préstamo con id: " + dto.getIdSeguroPrestamo()));
            SegurosPrestamoCliente entity = new SegurosPrestamoCliente();
            entity.setIdPrestamoCliente(prestamoCliente);
            entity.setIdSeguroPrestamo(seguroPrestamo);
            entity.setMontoTotal(dto.getMontoTotal());
            entity.setMontoCuota(dto.getMontoCuota());
            // entity.setEstado(dto.getEstado());
            entity.setEstado(EstadoGeneralEnum.ACTIVO.getValor());
            entity.setVersion(1L);
            SegurosPrestamoCliente guardado = segurosPrestamoClienteRepositorio.save(entity);
            return this.transformarClienteADTO(guardado);
        } catch (Exception e) {
            log.error("Error al crear la relación seguro-préstamo-cliente", e);
            throw new CreateException("Seguro-Préstamo-Cliente", "Error al crear: " + e.getMessage());
        }
    }

    @Transactional
    public SegurosPrestamoClienteDTO actualizarSeguroPrestamoCliente(Integer id, SegurosPrestamoClienteDTO dto) {
        log.info("Actualizando relación seguro-préstamo-cliente con ID: {}", id);
        try {
            SegurosPrestamoCliente entity = segurosPrestamoClienteRepositorio.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Seguro-Préstamo-Cliente",
                            "No se encontró la relación con id: " + id));
            if (!entity.getIdPrestamoCliente().getId().equals(dto.getIdPrestamoCliente())) {
                PrestamosCliente prestamoCliente = prestamosClienteRepository.findById(dto.getIdPrestamoCliente())
                        .orElseThrow(() -> new UpdateException("Seguro-Préstamo-Cliente",
                                "No existe el préstamo cliente con id: " + dto.getIdPrestamoCliente()));
                entity.setIdPrestamoCliente(prestamoCliente);
            }
            if (!entity.getIdSeguroPrestamo().getId().equals(dto.getIdSeguroPrestamo())) {
                SegurosPrestamo seguroPrestamo = segurosPrestamoRepositorio.findById(dto.getIdSeguroPrestamo())
                        .orElseThrow(() -> new UpdateException("Seguro-Préstamo-Cliente",
                                "No existe la relación seguro-préstamo con id: " + dto.getIdSeguroPrestamo()));
                entity.setIdSeguroPrestamo(seguroPrestamo);
            }
            entity.setMontoTotal(dto.getMontoTotal());
            entity.setMontoCuota(dto.getMontoCuota());
            if (dto.getEstado() != null) {
                entity.setEstado(dto.getEstado());
            }
            entity.setVersion(entity.getVersion() + 1L);
            SegurosPrestamoCliente actualizado = segurosPrestamoClienteRepositorio.save(entity);
            return this.transformarClienteADTO(actualizado);
        } catch (EntityNotFoundException | UpdateException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error al actualizar la relación seguro-préstamo-cliente", e);
            throw new UpdateException("Seguro-Préstamo-Cliente", "Error al actualizar: " + e.getMessage());
        }
    }

    @Transactional
    public void eliminarSeguroPrestamoCliente(Integer id) {
        log.info("Eliminando (soft) relación seguro-préstamo-cliente con ID: {}", id);
        SegurosPrestamoCliente entity = segurosPrestamoClienteRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seguro-Préstamo-Cliente",
                        "No se encontró la relación con id: " + id));
        entity.setEstado("INACTIVO");
        segurosPrestamoClienteRepositorio.save(entity);
    }

    // Transformadores
    private SegurosPrestamoDTO transformarADTO(SegurosPrestamo entity) {
        return SegurosPrestamoDTO.builder()
                .id(entity.getId())
                .idSeguro(entity.getIdSeguro().getId())
                .idPrestamo(entity.getIdPrestamo().getId())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }

    private SegurosPrestamoClienteDTO transformarClienteADTO(SegurosPrestamoCliente entity) {
        return SegurosPrestamoClienteDTO.builder()
                .id(entity.getId())
                .idPrestamoCliente(entity.getIdPrestamoCliente().getId())
                .idSeguroPrestamo(entity.getIdSeguroPrestamo().getId())
                .montoTotal(entity.getMontoTotal())
                .montoCuota(entity.getMontoCuota())
                .estado(entity.getEstado())
                .version(entity.getVersion())
                .build();
    }
}