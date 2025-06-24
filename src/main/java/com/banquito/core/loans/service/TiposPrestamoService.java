package com.banquito.core.loans.service;

import com.banquito.core.loans.DTO.EsquemasAmortizacionDTO;
import com.banquito.core.loans.DTO.TiposPrestamoDTO;
import com.banquito.core.loans.enums.EsquemaAmortizacionEnum;
import com.banquito.core.loans.enums.EstadoGeneralEnum;
import com.banquito.core.loans.enums.TipoClienteEnum;
import com.banquito.core.loans.exception.CreateException;
import com.banquito.core.loans.exception.EntityNotFoundException;
import com.banquito.core.loans.modelo.EsquemasAmortizacion;
import com.banquito.core.loans.modelo.TiposPrestamo;
import com.banquito.core.loans.repositorio.EsquemasAmortizacionRepositorio;
import com.banquito.core.loans.repositorio.TiposPrestamoRepositorio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TiposPrestamoService {

    private final TiposPrestamoRepositorio tiposPrestamoRepository;
    private final EsquemasAmortizacionRepositorio esquemaRepository;

    public TiposPrestamoService(TiposPrestamoRepositorio tiposPrestamoRepository,
            EsquemasAmortizacionRepositorio esquemaRepository) {
        this.tiposPrestamoRepository = tiposPrestamoRepository;
        this.esquemaRepository = esquemaRepository;
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
            tipoPrestamo.setVersion(1L);

            TiposPrestamo tipoPrestamoGuardado = this.tiposPrestamoRepository.save(tipoPrestamo);
            return this.transformarADTO(tipoPrestamoGuardado);
        } catch (Exception e) {
            log.error("Error al crear el tipo de préstamo", e);
            throw new CreateException("Tipo de Préstamo", "Error al crear el tipo de préstamo: " + e.getMessage());
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

    // esquema-amortizacion
    @Transactional
    public EsquemasAmortizacionDTO crearEsquemaAmortizacionTipoPrestamo(EsquemasAmortizacionDTO esquemaDTO) {
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
            esquema.setVersion(1L);

            EsquemasAmortizacion esquemaGuardado = this.esquemaRepository.save(esquema);
            return this.transformarADTO(esquemaGuardado);
        } catch (Exception e) {
            log.error("Error al crear el esquema de amortización", e);
            throw new CreateException("Esquema de Amortización", "Error al crear el esquema: " + e.getMessage());
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
