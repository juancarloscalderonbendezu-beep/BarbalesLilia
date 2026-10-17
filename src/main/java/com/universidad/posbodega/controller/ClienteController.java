package com.universidad.posbodega.controller;

import com.universidad.posbodega.dto.ClienteRequest;
import com.universidad.posbodega.dto.ClienteResponse;
import com.universidad.posbodega.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteResponse buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    @PostMapping
    public ClienteResponse crear(@Valid @RequestBody ClienteRequest request) {
        return clienteService.crear(request);
    }

    @PutMapping("/{id}")
    public ClienteResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ClienteRequest request
    ) {
        return clienteService.actualizar(id, request);
    }

    @PatchMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Long id) {
        clienteService.desactivar(id);
    }

    @PatchMapping("/{id}/activar")
    public void activar(@PathVariable Long id) {
        clienteService.activar(id);
    }
}