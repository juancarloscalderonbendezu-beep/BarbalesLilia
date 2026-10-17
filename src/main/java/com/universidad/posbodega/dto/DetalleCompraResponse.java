package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class DetalleCompraResponse {

    private Long productoId;
    private String productoNombre;
    private Integer cantidad;
    private BigDecimal precioCompra;
    private BigDecimal subtotal;
}