package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.CompraRequest;
import com.universidad.posbodega.dto.CompraResponse;

import java.util.List;

public interface CompraService {

    List<CompraResponse> listar();

    CompraResponse buscarPorId(Long id);

    CompraResponse registrar(CompraRequest request);

    void anular(Long id);
}