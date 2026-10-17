package com.universidad.posbodega.controller;

import com.universidad.posbodega.dto.ProveedorRequest;
import com.universidad.posbodega.dto.ProveedorResponse;
import com.universidad.posbodega.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @GetMapping
    public List<ProveedorResponse> listar() {
        return proveedorService.listar();
    }

    @GetMapping("/{id}")
    public ProveedorResponse buscarPorId(@PathVariable Long id) {
        return proveedorService.buscarPorId(id);
    }

    @PostMapping
    public ProveedorResponse crear(@Valid @RequestBody ProveedorRequest request) {
        return proveedorService.crear(request);
    }

    @PutMapping("/{id}")
    public ProveedorResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProveedorRequest request
    ) {
        return proveedorService.actualizar(id, request);
    }

    @PatchMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Long id) {
        proveedorService.desactivar(id);
    }

    @PatchMapping("/{id}/activar")
    public void activar(@PathVariable Long id) {
        proveedorService.activar(id);
    }
}