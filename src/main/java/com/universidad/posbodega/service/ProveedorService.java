package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.ProveedorRequest;
import com.universidad.posbodega.dto.ProveedorResponse;

import java.util.List;

public interface ProveedorService {

    List<ProveedorResponse> listar();

    ProveedorResponse buscarPorId(Long id);

    ProveedorResponse crear(ProveedorRequest request);

    ProveedorResponse actualizar(Long id, ProveedorRequest request);

    void desactivar(Long id);

    void activar(Long id);
}