package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CompraResponse {

    private Long id;
    private Long proveedorId;
    private String proveedorNombre;
    private LocalDateTime fecha;
    private String tipoComprobante;
    private String numeroComprobante;
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    private String estado;
    private List<DetalleCompraResponse> detalles;
}