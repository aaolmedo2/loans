package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.SeguroDTO;
import com.banquito.core.loans.service.SeguroService;
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
@RequestMapping("/api/v1/seguros")
@Tag(name = "Seguros", description = "API para gestionar seguros")
@Slf4j
public class SeguroController {

    private final SeguroService seguroService;

    public SeguroController(SeguroService seguroService) {
        this.seguroService = seguroService;
    }

    @Operation(summary = "Obtener todos los seguros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguros encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeguroDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay seguros", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<SeguroDTO>> obtenerTodos() {
        log.info("Obteniendo todos los seguros");
        List<SeguroDTO> seguros = this.seguroService.obtenerTodos();
        if (seguros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seguros);
    }

    @Operation(summary = "Obtener seguros por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguros encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeguroDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay seguros con ese estado", content = @Content)
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<SeguroDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del seguro") @PathVariable String estado) {
        log.info("Obteniendo seguros por estado: {}", estado);
        List<SeguroDTO> seguros = this.seguroService.obtenerPorEstado(estado);
        if (seguros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(seguros);
    }

    @Operation(summary = "Obtener seguro por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeguroDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<SeguroDTO> obtenerPorId(
            @Parameter(description = "ID del seguro") @PathVariable Integer id) {
        log.info("Obteniendo seguro por ID: {}", id);
        return ResponseEntity.ok(this.seguroService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo seguro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro creado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeguroDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos del seguro inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<SeguroDTO> crear(
            @Parameter(description = "Seguro a crear") @RequestBody SeguroDTO seguroDTO) {
        log.info("Creando nuevo seguro: {}", seguroDTO);
        return ResponseEntity.ok(this.seguroService.crear(seguroDTO));
    }

    @Operation(summary = "Actualizar un seguro existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seguro actualizado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = SeguroDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos del seguro inválidos", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<SeguroDTO> actualizar(
            @Parameter(description = "ID del seguro") @PathVariable Integer id,
            @Parameter(description = "Datos actualizados del seguro") @RequestBody SeguroDTO seguroDTO) {
        log.info("Actualizando seguro con ID: {} con datos: {}", id, seguroDTO);
        return ResponseEntity.ok(this.seguroService.actualizar(id, seguroDTO));
    }

    @Operation(summary = "Eliminar un seguro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seguro eliminado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Seguro no encontrado", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del seguro") @PathVariable Integer id) {
        log.info("Eliminando seguro con ID: {}", id);
        this.seguroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
