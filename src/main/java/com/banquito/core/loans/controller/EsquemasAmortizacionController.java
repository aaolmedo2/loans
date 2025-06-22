package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.EsquemasAmortizacionDTO;
import com.banquito.core.loans.service.EsquemasAmortizacionService;

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
@RequestMapping("/api/v1/esquemas-amortizacion")
@Tag(name = "Esquemas de Amortización", description = "API para gestionar esquemas de amortización")
@Slf4j
public class EsquemasAmortizacionController {

    private final EsquemasAmortizacionService esquemaService;

    public EsquemasAmortizacionController(EsquemasAmortizacionService esquemaService) {
        this.esquemaService = esquemaService;
    }

    @Operation(summary = "Obtener todos los esquemas de amortización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Esquemas de amortización encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EsquemasAmortizacionDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay esquemas de amortización", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<EsquemasAmortizacionDTO>> obtenerTodos() {
        log.info("Obteniendo todos los esquemas de amortización");
        List<EsquemasAmortizacionDTO> esquemas = this.esquemaService.obtenerTodos();
        if (esquemas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(esquemas);
    }

    @Operation(summary = "Obtener esquemas de amortización por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Esquemas de amortización encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EsquemasAmortizacionDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay esquemas de amortización con ese estado", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EsquemasAmortizacionDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del esquema de amortización") @PathVariable String estado) {
        log.info("Obteniendo esquemas de amortización por estado: {}", estado);
        List<EsquemasAmortizacionDTO> esquemas = this.esquemaService.obtenerPorEstado(estado);
        if (esquemas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(esquemas);
    }

    @Operation(summary = "Obtener esquema de amortización por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Esquema de amortización encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EsquemasAmortizacionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Esquema de amortización no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EsquemasAmortizacionDTO> obtenerPorId(
            @Parameter(description = "ID del esquema de amortización") @PathVariable Integer id) {
        log.info("Obteniendo esquema de amortización por ID: {}", id);
        return ResponseEntity.ok(this.esquemaService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo esquema de amortización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Esquema de amortización creado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EsquemasAmortizacionDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos del esquema de amortización inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EsquemasAmortizacionDTO> crear(
            @Parameter(description = "Esquema de amortización a crear") @RequestBody EsquemasAmortizacionDTO esquemaDTO) {
        log.info("Creando nuevo esquema de amortización: {}", esquemaDTO);
        return ResponseEntity.ok(this.esquemaService.crear(esquemaDTO));
    }

    @Operation(summary = "Actualizar un esquema de amortización existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Esquema de amortización actualizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EsquemasAmortizacionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Esquema de amortización no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos del esquema de amortización inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<EsquemasAmortizacionDTO> actualizar(
            @Parameter(description = "ID del esquema de amortización") @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del esquema de amortización") @RequestBody EsquemasAmortizacionDTO esquemaDTO) {
        log.info("Actualizando esquema de amortización con ID: {} con datos: {}", id, esquemaDTO);
        return ResponseEntity.ok(this.esquemaService.actualizar(id, esquemaDTO));
    }

    @Operation(summary = "Eliminar un esquema de amortización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Esquema de amortización eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Esquema de amortización no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del esquema de amortización") @PathVariable Integer id) {
        log.info("Eliminando esquema de amortización con ID: {}", id);
        this.esquemaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
