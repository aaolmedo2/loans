package com.banquito.core.loans.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.banquito.core.loans.DTO.PagosPrestamoDTO;
import com.banquito.core.loans.service.PagosPrestamoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/pagos-prestamos")
@Tag(name = "Pagos de Préstamos", description = "API para gestionar pagos de préstamos")
@Slf4j
public class PagosPrestamoController {

    private final PagosPrestamoService pagosPrestamoService;

    public PagosPrestamoController(PagosPrestamoService pagosPrestamoService) {
        this.pagosPrestamoService = pagosPrestamoService;
    }

    @Operation(summary = "Obtener todos los pagos de préstamos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pagos encontrados", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PagosPrestamoDTO.class)) }),
            @ApiResponse(responseCode = "204", description = "No hay pagos registrados", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PagosPrestamoDTO>> obtenerTodos() {
        log.info("Obteniendo todos los pagos de préstamos");
        List<PagosPrestamoDTO> pagos = this.pagosPrestamoService.obtenerTodos();
        if (pagos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @Operation(summary = "Obtener pago de préstamo por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago encontrado", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PagosPrestamoDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Pago no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PagosPrestamoDTO> obtenerPorId(
            @Parameter(description = "ID del pago") @PathVariable Integer id) {
        log.info("Obteniendo pago de préstamo por ID: {}", id);
        return ResponseEntity.ok(this.pagosPrestamoService.obtenerPorId(id));
    }

    @Operation(summary = "Registrar pago de préstamo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pago registrado correctamente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PagosPrestamoDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Error al registrar el pago", content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<PagosPrestamoDTO> registrarPago(
            @Parameter(description = "ID de la cuota") @RequestParam Integer idCuota,
            @Parameter(description = "Tipo de pago") @RequestParam String tipoPago,
            @Parameter(description = "Referencia del pago") @RequestParam String referencia) {
        log.info("Registrando pago para la cuota con ID: {}, tipo: {}, referencia: {}", idCuota, tipoPago, referencia);
        return ResponseEntity.ok(this.pagosPrestamoService.registrarPago(idCuota, tipoPago, referencia));
    }
}
