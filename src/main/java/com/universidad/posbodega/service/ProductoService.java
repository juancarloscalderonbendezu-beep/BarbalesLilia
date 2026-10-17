package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.ProductoRequest;
import com.universidad.posbodega.dto.ProductoResponse;

import java.util.List;

public interface ProductoService {

    List<ProductoResponse> listar();

    ProductoResponse buscarPorId(Long id);

    ProductoResponse crear(ProductoRequest request);

    ProductoResponse actualizar(Long id, ProductoRequest request);

    void desactivar(Long id);

    void activar(Long id);
}