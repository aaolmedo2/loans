package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.PrestamosClienteDTO;
import com.banquito.core.loans.service.PrestamosClienteService;
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
@RequestMapping("/api/v1/prestamos-clientes")
@Tag(name = "Préstamos de Clientes", description = "API para gestionar préstamos de clientes")
@Slf4j
public class PrestamosClienteController {

        private final PrestamosClienteService prestamosClienteService;

        public PrestamosClienteController(PrestamosClienteService prestamosClienteService) {
                this.prestamosClienteService = prestamosClienteService;
        }

        @Operation(summary = "Obtener todos los préstamos de clientes")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Préstamos de clientes encontrados", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamosClienteDTO.class)) }),
                        @ApiResponse(responseCode = "204", description = "No hay préstamos de clientes", content = @Content)
        })
        @GetMapping
        public ResponseEntity<List<PrestamosClienteDTO>> obtenerTodos() {
                log.info("Obteniendo todos los préstamos de clientes");
                List<PrestamosClienteDTO> prestamosClientes = this.prestamosClienteService.obtenerTodos();
                if (prestamosClientes.isEmpty()) {
                        return ResponseEntity.noContent().build();
                }
                return ResponseEntity.ok(prestamosClientes);
        }

        @Operation(summary = "Crear un nuevo préstamo de cliente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Préstamo de cliente creado", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PrestamosClienteDTO.class)) }),
                        @ApiResponse(responseCode = "400", description = "Datos del préstamo de cliente inválidos", content = @Content)
        })
        @PostMapping
        public ResponseEntity<PrestamosClienteDTO> crear(
                        @Parameter(description = "Préstamo de cliente a crear") @RequestBody PrestamosClienteDTO prestamoClienteDTO) {
                log.info("Creando nuevo préstamo de cliente: {}", prestamoClienteDTO);
                return ResponseEntity.ok(this.prestamosClienteService.crear(prestamoClienteDTO));
        }

        @Operation(summary = "Eliminar un préstamo de cliente")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Préstamo de cliente eliminado", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Préstamo de cliente no encontrado", content = @Content)
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> eliminar(
                        @Parameter(description = "ID del préstamo de cliente") @PathVariable Integer id) {
                log.info("Eliminando préstamo de cliente con ID: {}", id);
                this.prestamosClienteService.eliminar(id);
                return ResponseEntity.noContent().build();
        }
}
