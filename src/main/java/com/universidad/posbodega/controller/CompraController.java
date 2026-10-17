package com.universidad.posbodega.controller;

import com.universidad.posbodega.dto.CompraRequest;
import com.universidad.posbodega.dto.CompraResponse;
import com.universidad.posbodega.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public List<CompraResponse> listar() {
        return compraService.listar();
    }

    @GetMapping("/{id}")
    public CompraResponse buscarPorId(@PathVariable Long id) {
        return compraService.buscarPorId(id);
    }

    @PostMapping
    public CompraResponse registrar(@Valid @RequestBody CompraRequest request) {
        return compraService.registrar(request);
    }

    @PatchMapping("/{id}/anular")
    public void anular(@PathVariable Long id) {
        compraService.anular(id);
    }
}