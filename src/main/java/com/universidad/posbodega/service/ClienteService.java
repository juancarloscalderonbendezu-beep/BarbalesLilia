package com.universidad.posbodega.service;

import com.universidad.posbodega.dto.ClienteRequest;
import com.universidad.posbodega.dto.ClienteResponse;

import java.util.List;

public interface ClienteService {

    List<ClienteResponse> listar();

    ClienteResponse buscarPorId(Long id);

    ClienteResponse crear(ClienteRequest request);

    ClienteResponse actualizar(Long id, ClienteRequest request);

    void desactivar(Long id);

    void activar(Long id);
}