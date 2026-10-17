package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.CategoriaRequest;
import com.universidad.posbodega.dto.CategoriaResponse;
import com.universidad.posbodega.entity.Categoria;
import com.universidad.posbodega.repository.CategoriaRepository;
import com.universidad.posbodega.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = obtenerCategoria(id);
        return mapToResponse(categoria);
    }

    @Override
    public CategoriaResponse crear(CategoriaRequest request) {
        if (categoriaRepository.existsByNombre(request.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = Categoria.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .estado(true)
                .build();

        return mapToResponse(categoriaRepository.save(categoria));
    }

    @Override
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria categoria = obtenerCategoria(id);

        categoria.setNombre(request.getNombre());
        categoria.setDescripcion(request.getDescripcion());

        return mapToResponse(categoriaRepository.save(categoria));
    }

    @Override
    public void desactivar(Long id) {
        Categoria categoria = obtenerCategoria(id);
        categoria.setEstado(false);
        categoriaRepository.save(categoria);
    }

    @Override
    public void activar(Long id) {
        Categoria categoria = obtenerCategoria(id);
        categoria.setEstado(true);
        categoriaRepository.save(categoria);
    }

    private Categoria obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    private CategoriaResponse mapToResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .estado(categoria.getEstado())
                .build();
    }
}