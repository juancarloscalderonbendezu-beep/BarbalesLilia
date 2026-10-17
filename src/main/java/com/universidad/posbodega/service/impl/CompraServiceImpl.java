package com.universidad.posbodega.service.impl;

import com.universidad.posbodega.dto.*;
import com.universidad.posbodega.entity.*;
import com.universidad.posbodega.repository.*;
import com.universidad.posbodega.service.CompraService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;

    @Override
    public List<CompraResponse> listar() {
        return compraRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CompraResponse buscarPorId(Long id) {
        Compra compra = obtenerCompra(id);
        return mapToResponse(compra);
    }

    @Override
    @Transactional
    public CompraResponse registrar(CompraRequest request) {
        Proveedor proveedor = proveedorRepository.findById(request.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Compra compra = Compra.builder()
                .proveedor(proveedor)
                .fecha(LocalDateTime.now())
                .tipoComprobante(request.getTipoComprobante())
                .numeroComprobante(request.getNumeroComprobante())
                .estado("REGISTRADA")
                .subtotal(BigDecimal.ZERO)
                .igv(BigDecimal.ZERO)
                .total(BigDecimal.ZERO)
                .detalles(new ArrayList<>())
                .build();

        BigDecimal subtotalCompra = BigDecimal.ZERO;

        for (DetalleCompraRequest detalleRequest : request.getDetalles()) {
            Producto producto = productoRepository.findById(detalleRequest.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            BigDecimal subtotalDetalle = detalleRequest.getPrecioCompra()
                    .multiply(BigDecimal.valueOf(detalleRequest.getCantidad()));

            DetalleCompra detalle = DetalleCompra.builder()
                    .compra(compra)
                    .producto(producto)
                    .cantidad(detalleRequest.getCantidad())
                    .precioCompra(detalleRequest.getPrecioCompra())
                    .subtotal(subtotalDetalle)
                    .build();

            compra.getDetalles().add(detalle);

            producto.setStockActual(producto.getStockActual() + detalleRequest.getCantidad());
            producto.setPrecioCompra(detalleRequest.getPrecioCompra());

            productoRepository.save(producto);

            subtotalCompra = subtotalCompra.add(subtotalDetalle);
        }

        BigDecimal igv = subtotalCompra.multiply(new BigDecimal("0.18"));
        BigDecimal total = subtotalCompra.add(igv);

        compra.setSubtotal(subtotalCompra);
        compra.setIgv(igv);
        compra.setTotal(total);

        Compra compraGuardada = compraRepository.save(compra);

        return mapToResponse(compraGuardada);
    }

    @Override
    @Transactional
    public void anular(Long id) {
        Compra compra = obtenerCompra(id);

        if ("ANULADA".equals(compra.getEstado())) {
            throw new RuntimeException("La compra ya se encuentra anulada");
        }

        for (DetalleCompra detalle : compra.getDetalles()) {
            Producto producto = detalle.getProducto();

            int nuevoStock = producto.getStockActual() - detalle.getCantidad();

            if (nuevoStock < 0) {
                throw new RuntimeException("No se puede anular la compra porque el stock quedaría negativo");
            }

            producto.setStockActual(nuevoStock);
            productoRepository.save(producto);
        }

        compra.setEstado("ANULADA");
        compraRepository.save(compra);
    }

    private Compra obtenerCompra(Long id) {
        return compraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
    }

    private CompraResponse mapToResponse(Compra compra) {
        return CompraResponse.builder()
                .id(compra.getId())
                .proveedorId(compra.getProveedor().getId())
                .proveedorNombre(compra.getProveedor().getRazonSocial())
                .fecha(compra.getFecha())
                .tipoComprobante(compra.getTipoComprobante())
                .numeroComprobante(compra.getNumeroComprobante())
                .subtotal(compra.getSubtotal())
                .igv(compra.getIgv())
                .total(compra.getTotal())
                .estado(compra.getEstado())
                .detalles(
                        compra.getDetalles()
                                .stream()
                                .map(this::mapDetalleToResponse)
                                .toList()
                )
                .build();
    }

    private DetalleCompraResponse mapDetalleToResponse(DetalleCompra detalle) {
        return DetalleCompraResponse.builder()
                .productoId(detalle.getProducto().getId())
                .productoNombre(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioCompra(detalle.getPrecioCompra())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}