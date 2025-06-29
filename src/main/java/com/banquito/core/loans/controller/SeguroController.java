package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.SeguroDTO;
import com.banquito.core.loans.DTO.MontoSeguroDTO;
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

        @Operation(summary = "Actualizar el monto asegurado de un seguro existente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Monto asegurado actualizado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = SeguroDTO.class)) }),
                        @ApiResponse(responseCode = "404", description = "Seguro no encontrado", content = @Content),
                        @ApiResponse(responseCode = "400", description = "Monto asegurado inválido", content = @Content)
        })
        @PutMapping("/{id}")
        public ResponseEntity<SeguroDTO> actualizar(
                        @Parameter(description = "ID del seguro") @PathVariable Integer id,
                        @Parameter(description = "Nuevo monto asegurado") @RequestBody MontoSeguroDTO montoDTO) {
                log.info("Actualizando monto asegurado del seguro con ID: {} con valor: {}", id,
                                montoDTO.getMontoAsegurado());

                // Crear un DTO con solo el monto asegurado para la actualización
                SeguroDTO seguroDTO = SeguroDTO.builder()
                                .montoAsegurado(montoDTO.getMontoAsegurado())
                                .build();

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
