package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.ComisionesPrestamoClienteDTO;
import com.banquito.core.loans.service.ComisionesPrestamoClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping("/api/comisiones-prestamo-cliente")
@Tag(name = "Comisiones de Préstamos de Clientes", description = "API para gestionar las comisiones asociadas a los préstamos de clientes")
@Slf4j
public class ComisionesPrestamoClienteController {

    private final ComisionesPrestamoClienteService comisionesPrestamoClienteService;

    public ComisionesPrestamoClienteController(ComisionesPrestamoClienteService comisionesPrestamoClienteService) {
        this.comisionesPrestamoClienteService = comisionesPrestamoClienteService;
    }

    @Operation(summary = "Obtener todas las comisiones de préstamos de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comisiones de préstamos de clientes encontradas", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ComisionesPrestamoClienteDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay comisiones de préstamos de clientes", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<ComisionesPrestamoClienteDTO>> obtenerTodos() {
        log.info("Obteniendo todas las comisiones de préstamos de clientes");
        List<ComisionesPrestamoClienteDTO> comisiones = this.comisionesPrestamoClienteService.obtenerTodos();
        if (comisiones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comisiones);
    }

    @Operation(summary = "Crear una nueva comisión de préstamo de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comisión de préstamo de cliente creada correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ComisionesPrestamoClienteDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Datos inválidos para crear la comisión", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ComisionesPrestamoClienteDTO> crear(
            @RequestBody ComisionesPrestamoClienteDTO comisionesPrestamoClienteDTO) {
        log.info("Creando nueva comisión de préstamo de cliente: {}", comisionesPrestamoClienteDTO);
        return ResponseEntity.ok(this.comisionesPrestamoClienteService.crear(comisionesPrestamoClienteDTO));
    }

    @Operation(summary = "Eliminar una comisión de préstamo de cliente (cambio de estado a CANCELADA)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comisión de préstamo de cliente eliminada correctamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comisión de préstamo de cliente no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        log.info("Eliminando comisión de préstamo de cliente con ID: {}", id);
        this.comisionesPrestamoClienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
