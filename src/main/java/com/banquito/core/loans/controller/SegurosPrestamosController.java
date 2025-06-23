package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.SegurosPrestamoDTO;
import com.banquito.core.loans.DTO.SegurosPrestamoClienteDTO;
import com.banquito.core.loans.service.SegurosPrestamosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/seguros-prestamos")
@Tag(name = "Seguros-Préstamos", description = "API para gestionar relaciones de seguros y préstamos")
@Slf4j
public class SegurosPrestamosController {
    private final SegurosPrestamosService service;

    public SegurosPrestamosController(SegurosPrestamosService service) {
        this.service = service;
    }

    // --- SegurosPrestamo ---
    @Operation(summary = "Obtener todas las relaciones seguro-préstamo")
    @GetMapping
    public ResponseEntity<List<SegurosPrestamoDTO>> obtenerTodosSegurosPrestamo() {
        log.info("Obteniendo todas las relaciones seguro-préstamo");
        List<SegurosPrestamoDTO> lista = service.obtenerTodosSegurosPrestamo();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener relación seguro-préstamo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<SegurosPrestamoDTO> obtenerSeguroPrestamoPorId(@PathVariable Integer id) {
        log.info("Obteniendo relación seguro-préstamo por ID: {}", id);
        return ResponseEntity.ok(service.obtenerSeguroPrestamoPorId(id));
    }

    @Operation(summary = "Crear nueva relación seguro-préstamo")
    @PostMapping
    public ResponseEntity<SegurosPrestamoDTO> crearSeguroPrestamo(@RequestBody SegurosPrestamoDTO dto) {
        log.info("Creando nueva relación seguro-préstamo: {}", dto);
        return ResponseEntity.ok(service.crearSeguroPrestamo(dto));
    }

    @Operation(summary = "Eliminar (soft) relación seguro-préstamo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSeguroPrestamo(@PathVariable Integer id) {
        log.info("Eliminando (soft) relación seguro-préstamo con ID: {}", id);
        service.eliminarSeguroPrestamo(id);
        return ResponseEntity.noContent().build();
    }

    // --- SegurosPrestamoCliente ---
    @Operation(summary = "Obtener todas las relaciones seguro-préstamo-cliente")
    @GetMapping("/clientes")
    public ResponseEntity<List<SegurosPrestamoClienteDTO>> obtenerTodosSegurosPrestamoCliente() {
        log.info("Obteniendo todas las relaciones seguro-préstamo-cliente");
        List<SegurosPrestamoClienteDTO> lista = service.obtenerTodosSegurosPrestamoCliente();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener relación seguro-préstamo-cliente por ID")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<SegurosPrestamoClienteDTO> obtenerSeguroPrestamoClientePorId(@PathVariable Integer id) {
        log.info("Obteniendo relación seguro-préstamo-cliente por ID: {}", id);
        return ResponseEntity.ok(service.obtenerSeguroPrestamoClientePorId(id));
    }

    @Operation(summary = "Crear nueva relación seguro-préstamo-cliente")
    @PostMapping("/clientes")
    public ResponseEntity<SegurosPrestamoClienteDTO> crearSeguroPrestamoCliente(
            @RequestBody SegurosPrestamoClienteDTO dto) {
        log.info("Creando nueva relación seguro-préstamo-cliente: {}", dto);
        return ResponseEntity.ok(service.crearSeguroPrestamoCliente(dto));
    }

    @Operation(summary = "Actualizar relación seguro-préstamo-cliente")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<SegurosPrestamoClienteDTO> actualizarSeguroPrestamoCliente(@PathVariable Integer id,
            @RequestBody SegurosPrestamoClienteDTO dto) {
        log.info("Actualizando relación seguro-préstamo-cliente con ID: {}", id);
        return ResponseEntity.ok(service.actualizarSeguroPrestamoCliente(id, dto));
    }

    @Operation(summary = "Eliminar (soft) relación seguro-préstamo-cliente")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarSeguroPrestamoCliente(@PathVariable Integer id) {
        log.info("Eliminando (soft) relación seguro-préstamo-cliente con ID: {}",
                id);
        service.eliminarSeguroPrestamoCliente(id);
        return ResponseEntity.noContent().build();
    }
}