package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.PrestamoDTO;
import com.banquito.core.loans.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prestamos")
@Tag(name = "Préstamos", description = "API para gestionar préstamos")
@Slf4j
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @Operation(summary = "Obtener todos los préstamos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay préstamos", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PrestamoDTO>> obtenerTodos() {
        log.info("Obteniendo todos los préstamos");
        List<PrestamoDTO> prestamos = this.prestamoService.obtenerTodos();
        if (prestamos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Buscar préstamos por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay préstamos con ese nombre", content = @Content)
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<PrestamoDTO>> buscarPorNombre(
            @Parameter(description = "Nombre del préstamo") @PathVariable String nombre) {
        log.info("Buscando préstamos por nombre: {}", nombre);
        List<PrestamoDTO> prestamos = this.prestamoService.buscarPorNombre(nombre);
        if (prestamos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Obtener préstamos por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay préstamos con ese estado", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PrestamoDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del préstamo") @PathVariable String estado) {
        log.info("Obteniendo préstamos por estado: {}", estado);
        List<PrestamoDTO> prestamos = this.prestamoService.obtenerPorEstado(estado);
        if (prestamos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Obtener préstamos por tipo de préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamos encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay préstamos con ese tipo de préstamo", content = @Content)
    })
    @GetMapping("/tipo-prestamo/{idTipoPrestamo}")
    public ResponseEntity<List<PrestamoDTO>> obtenerPorTipoPrestamo(
            @Parameter(description = "ID del tipo de préstamo") @PathVariable Integer idTipoPrestamo) {
        log.info("Obteniendo préstamos por tipo de préstamo con ID: {}", idTipoPrestamo);
        List<PrestamoDTO> prestamos = this.prestamoService.obtenerPorTipoPrestamo(idTipoPrestamo);
        if (prestamos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(prestamos);
    }

    @Operation(summary = "Obtener préstamo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> obtenerPorId(
            @Parameter(description = "ID del préstamo") @PathVariable Integer id) {
        log.info("Obteniendo préstamo por ID: {}", id);
        return ResponseEntity.ok(this.prestamoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo creado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos del préstamo inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PrestamoDTO> crear(
            @Parameter(description = "Préstamo a crear") @RequestBody PrestamoDTO prestamoDTO) {
        log.info("Creando nuevo préstamo: {}", prestamoDTO);
        return ResponseEntity.ok(this.prestamoService.crear(prestamoDTO));
    }

    @Operation(summary = "Actualizar un préstamo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Préstamo actualizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos del préstamo inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<PrestamoDTO> actualizar(
            @Parameter(description = "ID del préstamo") @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del préstamo") @RequestBody PrestamoDTO prestamoDTO) {
        log.info("Actualizando préstamo con ID: {} con datos: {}", id, prestamoDTO);
        return ResponseEntity.ok(this.prestamoService.actualizar(id, prestamoDTO));
    }

    @Operation(summary = "Eliminar un préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Préstamo eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Préstamo no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del préstamo") @PathVariable Integer id) {
        log.info("Eliminando préstamo con ID: {}", id);
        this.prestamoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
