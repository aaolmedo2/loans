package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.EsquemasAmortizacionDTO;
import com.banquito.core.loans.DTO.TiposPrestamoDTO;
import com.banquito.core.loans.service.TiposPrestamoService;
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
@RequestMapping("/api/v1/tipos-prestamos")
@Tag(name = "Tipos de Préstamos", description = "API para gestionar tipos de préstamos")
@Slf4j
public class TiposPrestamoController {

        private final TiposPrestamoService tiposPrestamoService;

        public TiposPrestamoController(TiposPrestamoService tiposPrestamoService) {
                this.tiposPrestamoService = tiposPrestamoService;
        }

        @Operation(summary = "Obtener todos los tipos de préstamos")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tipos de préstamos encontrados", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TiposPrestamoDTO.class)) }),
                        @ApiResponse(responseCode = "204", description = "No hay tipos de préstamos", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<TiposPrestamoDTO>> obtenerTodos() {
                log.info("Obteniendo todos los tipos de préstamos");
                List<TiposPrestamoDTO> tiposPrestamos = this.tiposPrestamoService.obtenerTodos();
                if (tiposPrestamos.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(tiposPrestamos);
        }

        @Operation(summary = "Obtener tipo de préstamo por ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tipo de préstamo encontrado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TiposPrestamoDTO.class)) }),
                        @ApiResponse(responseCode = "404", description = "Tipo de préstamo no encontrado", content = @Content)
        })
        @GetMapping("/{id}")
        public ResponseEntity<TiposPrestamoDTO> obtenerPorId(
                        @Parameter(description = "ID del tipo de préstamo") @PathVariable Integer id) {
                log.info("Obteniendo tipo de préstamo por ID: {}", id);
                return ResponseEntity.ok(this.tiposPrestamoService.obtenerPorId(id));
        }

        @Operation(summary = "Crear un nuevo tipo de préstamo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Tipo de préstamo creado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = TiposPrestamoDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Datos del tipo de préstamo inválidos", content = @Content)
        })
        @PostMapping
        public ResponseEntity<TiposPrestamoDTO> crear(
                        @Parameter(description = "Tipo de préstamo a crear") @RequestBody TiposPrestamoDTO tipoPrestamoDTO) {
                log.info("Creando nuevo tipo de préstamo: {}", tipoPrestamoDTO);
                return ResponseEntity.ok(this.tiposPrestamoService.crear(tipoPrestamoDTO));
        }

        @Operation(summary = "Eliminar un tipo de préstamo")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Tipo de préstamo eliminado", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Tipo de préstamo no encontrado", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(
                        @Parameter(description = "ID del tipo de préstamo") @PathVariable Integer id) {
                log.info("Eliminando tipo de préstamo con ID: {}", id);
                this.tiposPrestamoService.eliminar(id);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Crear un nuevo esquema de amortización")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Esquema de amortización creado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = EsquemasAmortizacionDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Datos del esquema de amortización inválidos", content = @Content)
        })
        @PostMapping("/esquemas-amortizacion")
        public ResponseEntity<EsquemasAmortizacionDTO> crear(
                        @Parameter(description = "Esquema de amortización a crear") @RequestBody EsquemasAmortizacionDTO esquemaDTO) {
                log.info("Creando nuevo esquema de amortización: {}", esquemaDTO);
                return ResponseEntity.ok(this.tiposPrestamoService.crearEsquemaAmortizacionTipoPrestamo(esquemaDTO));
        }
}
