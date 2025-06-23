package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.GarantiaDTO;
import com.banquito.core.loans.service.GarantiaService;
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
@RequestMapping("/api/v1/garantias")
@Tag(name = "Garantías", description = "API para gestionar garantías")
@Slf4j
public class GarantiaController {

        private final GarantiaService garantiaService;

        public GarantiaController(GarantiaService garantiaService) {
                this.garantiaService = garantiaService;
        }

        @Operation(summary = "Obtener todas las garantías")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Garantías encontradas", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GarantiaDTO.class)) }),
                        @ApiResponse(responseCode = "204", description = "No hay garantías", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<GarantiaDTO>> obtenerTodas() {
                log.info("Obteniendo todas las garantías");
                List<GarantiaDTO> garantias = this.garantiaService.obtenerTodas();
                if (garantias.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(garantias);
        }

        @Operation(summary = "Crear una nueva garantía")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Garantía creada", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GarantiaDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Datos de la garantía inválidos", content = @Content)
        })
        @PostMapping
        public ResponseEntity<GarantiaDTO> crear(
                        @Parameter(description = "Garantía a crear") @RequestBody GarantiaDTO garantiaDTO) {
                log.info("Creando nueva garantía: {}", garantiaDTO);
                return ResponseEntity.ok(this.garantiaService.crear(garantiaDTO));
        }

        @Operation(summary = "Actualizar una garantía existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Garantía actualizada", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = GarantiaDTO.class)) }),
                        @ApiResponse(responseCode = "404", description = "Garantía no encontrada", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Datos de la garantía inválidos", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<GarantiaDTO> actualizar(
                        @Parameter(description = "ID de la garantía") @PathVariable Integer id,
                        @Parameter(description = "Datos actualizados de la garantía") @RequestBody GarantiaDTO garantiaDTO) {
                log.info("Actualizando garantía con ID: {} con datos: {}", id, garantiaDTO);
                return ResponseEntity.ok(this.garantiaService.actualizar(id, garantiaDTO));
        }

        @Operation(summary = "Eliminar una garantía")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Garantía eliminada", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Garantía no encontrada", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(
                        @Parameter(description = "ID de la garantía") @PathVariable Integer id) {
                log.info("Eliminando garantía con ID: {}", id);
                this.garantiaService.eliminar(id);
                return ResponseEntity.noContent().build();
        }
}
