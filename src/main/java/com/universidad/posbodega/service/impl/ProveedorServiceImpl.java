package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.ProveedorRequest;
import com.universidad.posbodega.dto.ProveedorResponse;
import com.universidad.posbodega.entity.Proveedor;
import com.universidad.posbodega.repository.ProveedorRepository;
import com.universidad.posbodega.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public List<ProveedorResponse> listar() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProveedorResponse buscarPorId(Long id) {
        Proveedor proveedor = obtenerProveedor(id);
        return mapToResponse(proveedor);
    }

    @Override
    public ProveedorResponse crear(ProveedorRequest request) {
        if (request.getRuc() != null && !request.getRuc().isBlank()
                && proveedorRepository.existsByRuc(request.getRuc())) {
            throw new RuntimeException("Ya existe un proveedor con ese RUC");
        }

        Proveedor proveedor = Proveedor.builder()
                .razonSocial(request.getRazonSocial())
                .ruc(request.getRuc())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .email(request.getEmail())
                .estado(true)
                .build();

        return mapToResponse(proveedorRepository.save(proveedor));
    }

    @Override
    public ProveedorResponse actualizar(Long id, ProveedorRequest request) {
        Proveedor proveedor = obtenerProveedor(id);

        proveedor.setRazonSocial(request.getRazonSocial());
        proveedor.setRuc(request.getRuc());
        proveedor.setTelefono(request.getTelefono());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setEmail(request.getEmail());

        return mapToResponse(proveedorRepository.save(proveedor));
    }

    @Override
    public void desactivar(Long id) {
        Proveedor proveedor = obtenerProveedor(id);
        proveedor.setEstado(false);
        proveedorRepository.save(proveedor);
    }

    @Override
    public void activar(Long id) {
        Proveedor proveedor = obtenerProveedor(id);
        proveedor.setEstado(true);
        proveedorRepository.save(proveedor);
    }

    private Proveedor obtenerProveedor(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    private ProveedorResponse mapToResponse(Proveedor proveedor) {
        return ProveedorResponse.builder()
                .id(proveedor.getId())
                .razonSocial(proveedor.getRazonSocial())
                .ruc(proveedor.getRuc())
                .telefono(proveedor.getTelefono())
                .direccion(proveedor.getDireccion())
                .email(proveedor.getEmail())
                .estado(proveedor.getEstado())
                .build();
    }
}