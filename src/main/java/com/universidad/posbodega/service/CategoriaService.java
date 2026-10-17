package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.CategoriaRequest;
import com.universidad.posbodega.dto.CategoriaResponse;

import java.util.List;

public interface CategoriaService {

    List<CategoriaResponse> listar();

    CategoriaResponse buscarPorId(Long id);

    CategoriaResponse crear(CategoriaRequest request);

    CategoriaResponse actualizar(Long id, CategoriaRequest request);

    void desactivar(Long id);

    void activar(Long id);
}