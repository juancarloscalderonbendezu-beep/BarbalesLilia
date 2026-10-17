package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.ProductoRequest;
import com.universidad.posbodega.dto.ProductoResponse;
import com.universidad.posbodega.entity.Categoria;
import com.universidad.posbodega.entity.Producto;
import com.universidad.posbodega.repository.CategoriaRepository;
import com.universidad.posbodega.repository.ProductoRepository;
import com.universidad.posbodega.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    @Override
    public List<ProductoResponse> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductoResponse buscarPorId(Long id) {
        Producto producto = obtenerProducto(id);
        return mapToResponse(producto);
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        if (productoRepository.existsByCodigo(request.getCodigo())) {
            throw new RuntimeException("Ya existe un producto con ese código");
        }

        Categoria categoria = obtenerCategoria(request.getCategoriaId());

        Producto producto = Producto.builder()
                .categoria(categoria)
                .codigo(request.getCodigo())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .precioCompra(request.getPrecioCompra())
                .precioVenta(request.getPrecioVenta())
                .stockActual(request.getStockActual())
                .stockMinimo(request.getStockMinimo())
                .estado(true)
                .build();

        return mapToResponse(productoRepository.save(producto));
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = obtenerProducto(id);
        Categoria categoria = obtenerCategoria(request.getCategoriaId());

        producto.setCategoria(categoria);
        producto.setCodigo(request.getCodigo());
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecioCompra(request.getPrecioCompra());
        producto.setPrecioVenta(request.getPrecioVenta());
        producto.setStockActual(request.getStockActual());
        producto.setStockMinimo(request.getStockMinimo());

        return mapToResponse(productoRepository.save(producto));
    }

    @Override
    public void desactivar(Long id) {
        Producto producto = obtenerProducto(id);
        producto.setEstado(false);
        productoRepository.save(producto);
    }

    @Override
    public void activar(Long id) {
        Producto producto = obtenerProducto(id);
        producto.setEstado(true);
        productoRepository.save(producto);
    }

    private Producto obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    private Categoria obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    private ProductoResponse mapToResponse(Producto producto) {
        return ProductoResponse.builder()
                .id(producto.getId())
                .categoriaId(producto.getCategoria().getId())
                .categoriaNombre(producto.getCategoria().getNombre())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precioCompra(producto.getPrecioCompra())
                .precioVenta(producto.getPrecioVenta())
                .stockActual(producto.getStockActual())
                .stockMinimo(producto.getStockMinimo())
                .estado(producto.getEstado())
                .build();
    }
}