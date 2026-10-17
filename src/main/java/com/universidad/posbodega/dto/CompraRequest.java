package com.universidad.posbodega.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompraRequest {

    @NotNull(message = "El proveedor es obligatorio")
    private Long proveedorId;

    private String tipoComprobante;

    private String numeroComprobante;

    @NotEmpty(message = "Debe ingresar al menos un producto")
    @Valid
    private List<DetalleCompraRequest> detalles;
}