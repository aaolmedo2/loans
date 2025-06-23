package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.TiposComisionDTO;
import com.banquito.core.loans.service.TiposComisionService;
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
@RequestMapping("/api/v1/tipos-comisiones")
@Tag(name = "Tipos de Comisiones", description = "API para gestionar tipos de comisiones")
@Slf4j
public class TiposComisionController {

        private final TiposComisionService tiposComisionService;

        public TiposComisionController(TiposComisionService tiposComisionService) {
                this.tiposComisionService = tiposComisionService;
        }

        @Operation(summary = "Obtener todos los tipos de comisiones")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tipos de comisiones encontrados", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TiposComisionDTO.class)) }),
                        @ApiResponse(responseCode = "204", description = "No hay tipos de comisiones", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<TiposComisionDTO>> obtenerTodos() {
                log.info("Obteniendo todos los tipos de comisiones");
                List<TiposComisionDTO> tiposComisiones = this.tiposComisionService.obtenerTodos();
                if (tiposComisiones.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(tiposComisiones);
        }

        @Operation(summary = "Crear un nuevo tipo de comisión")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tipo de comisión creado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TiposComisionDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Datos del tipo de comisión inválidos", content = @Content)
        })
        @PostMapping
        public ResponseEntity<TiposComisionDTO> crear(
                        @Parameter(description = "Tipo de comisión a crear") @RequestBody TiposComisionDTO tipoComisionDTO) {
                log.info("Creando nuevo tipo de comisión: {}", tipoComisionDTO);
                return ResponseEntity.ok(this.tiposComisionService.crear(tipoComisionDTO));
        }

        @Operation(summary = "Actualizar un tipo de comisión existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tipo de comisión actualizado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TiposComisionDTO.class)) }),
                        @ApiResponse(responseCode = "404", description = "Tipo de comisión no encontrado", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Datos del tipo de comisión inválidos", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<TiposComisionDTO> actualizar(
                        @Parameter(description = "ID del tipo de comisión") @PathVariable Integer id,
                        @Parameter(description = "Datos actualizados del tipo de comisión") @RequestBody TiposComisionDTO tipoComisionDTO) {
                log.info("Actualizando tipo de comisión con ID: {} con datos: {}", id, tipoComisionDTO);
                return ResponseEntity.ok(this.tiposComisionService.actualizar(id, tipoComisionDTO));
        }

        @Operation(summary = "Eliminar un tipo de comisión")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Tipo de comisión eliminado", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Tipo de comisión no encontrado", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(
                        @Parameter(description = "ID del tipo de comisión") @PathVariable Integer id) {
                log.info("Eliminando tipo de comisión con ID: {}", id);
                this.tiposComisionService.eliminar(id);
                return ResponseEntity.noContent().build();
        }
}
