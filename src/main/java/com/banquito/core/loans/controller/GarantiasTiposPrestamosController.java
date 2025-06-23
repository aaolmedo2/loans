package com.banquito.core.loans.controller;

import com.banquito.core.loans.DTO.GarantiasTiposPrestamoDTO;
import com.banquito.core.loans.DTO.GarantiasTiposPrestamosClienteDTO;
import com.banquito.core.loans.service.GarantiasTiposPrestamosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/garantias-tipos-prestamos")
@Tag(name = "Garantías-Tipos-Préstamos", description = "API para gestionar relaciones de garantías y tipos de préstamos")
@Slf4j
public class GarantiasTiposPrestamosController {
    private final GarantiasTiposPrestamosService service;

    public GarantiasTiposPrestamosController(GarantiasTiposPrestamosService service) {
        this.service = service;
    }

    // --- GarantiasTiposPrestamo ---
    @Operation(summary = "Obtener todas las relaciones garantía-tipo préstamo")
    @GetMapping
    public ResponseEntity<List<GarantiasTiposPrestamoDTO>> obtenerTodosTipos() {
        log.info("Obteniendo todas las relaciones garantía-tipo préstamo");
        List<GarantiasTiposPrestamoDTO> lista = service.obtenerTodosTipos();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener relación garantía-tipo préstamo por ID")
    @GetMapping("/{id}")
    public ResponseEntity<GarantiasTiposPrestamoDTO> obtenerTipoPorId(@PathVariable Integer id) {
        log.info("Obteniendo relación garantía-tipo préstamo por ID: {}", id);
        return ResponseEntity.ok(service.obtenerTipoPorId(id));
    }

    @Operation(summary = "Crear nueva relación garantía-tipo préstamo")
    @PostMapping
    public ResponseEntity<GarantiasTiposPrestamoDTO> crearTipo(@RequestBody GarantiasTiposPrestamoDTO dto) {
        log.info("Creando nueva relación garantía-tipo préstamo: {}", dto);
        return ResponseEntity.ok(service.crearTipo(dto));
    }

    @Operation(summary = "Eliminar (soft) relación garantía-tipo préstamo")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipo(@PathVariable Integer id) {
        log.info("Eliminando (soft) relación garantía-tipo préstamo con ID: {}", id);
        service.eliminarTipo(id);
        return ResponseEntity.noContent().build();
    }

    // --- GarantiasTiposPrestamosCliente ---
    @Operation(summary = "Obtener todas las relaciones garantía-tipo préstamo-cliente")
    @GetMapping("/clientes")
    public ResponseEntity<List<GarantiasTiposPrestamosClienteDTO>> obtenerTodosClientes() {
        log.info("Obteniendo todas las relaciones garantía-tipo préstamo-cliente");
        List<GarantiasTiposPrestamosClienteDTO> lista = service.obtenerTodosClientes();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Obtener relación garantía-tipo préstamo-cliente por ID")
    @GetMapping("/clientes/{id}")
    public ResponseEntity<GarantiasTiposPrestamosClienteDTO> obtenerClientePorId(@PathVariable Integer id) {
        log.info("Obteniendo relación garantía-tipo préstamo-cliente por ID: {}", id);
        return ResponseEntity.ok(service.obtenerClientePorId(id));
    }

    @Operation(summary = "Crear nueva relación garantía-tipo préstamo-cliente")
    @PostMapping("/clientes")
    public ResponseEntity<GarantiasTiposPrestamosClienteDTO> crearCliente(
            @RequestBody GarantiasTiposPrestamosClienteDTO dto) {
        log.info("Creando nueva relación garantía-tipo préstamo-cliente: {}", dto);
        return ResponseEntity.ok(service.crearCliente(dto));
    }

    @Operation(summary = "Actualizar relación garantía-tipo préstamo-cliente")
    @PutMapping("/clientes/{id}")
    public ResponseEntity<GarantiasTiposPrestamosClienteDTO> actualizarCliente(@PathVariable Integer id,
            @RequestBody GarantiasTiposPrestamosClienteDTO dto) {
        log.info("Actualizando relación garantía-tipo préstamo-cliente con ID: {}", id);
        return ResponseEntity.ok(service.actualizarCliente(id, dto));
    }

    @Operation(summary = "Eliminar (soft) relación garantía-tipo préstamo-cliente")
    @DeleteMapping("/clientes/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        log.info("Eliminando (soft) relación garantía-tipo préstamo-cliente con ID: {}", id);
        service.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}