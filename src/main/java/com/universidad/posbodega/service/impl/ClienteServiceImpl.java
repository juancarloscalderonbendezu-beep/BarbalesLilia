package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.ClienteRequest;
import com.universidad.posbodega.dto.ClienteResponse;
import com.universidad.posbodega.entity.Cliente;
import com.universidad.posbodega.repository.ClienteRepository;
import com.universidad.posbodega.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public List<ClienteResponse> listar() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ClienteResponse buscarPorId(Long id) {
        Cliente cliente = obtenerCliente(id);
        return mapToResponse(cliente);
    }

    @Override
    public ClienteResponse crear(ClienteRequest request) {
        if (request.getDocumento() != null && !request.getDocumento().isBlank()
                && clienteRepository.existsByDocumento(request.getDocumento())) {
            throw new RuntimeException("Ya existe un cliente con ese documento");
        }

        Cliente cliente = Cliente.builder()
                .nombres(request.getNombres())
                .documento(request.getDocumento())
                .telefono(request.getTelefono())
                .direccion(request.getDireccion())
                .estado(true)
                .build();

        return mapToResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = obtenerCliente(id);

        cliente.setNombres(request.getNombres());
        cliente.setDocumento(request.getDocumento());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        return mapToResponse(clienteRepository.save(cliente));
    }

    @Override
    public void desactivar(Long id) {
        Cliente cliente = obtenerCliente(id);
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    @Override
    public void activar(Long id) {
        Cliente cliente = obtenerCliente(id);
        cliente.setEstado(true);
        clienteRepository.save(cliente);
    }

    private Cliente obtenerCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    private ClienteResponse mapToResponse(Cliente cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombres(cliente.getNombres())
                .documento(cliente.getDocumento())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .estado(cliente.getEstado())
                .build();
    }
}