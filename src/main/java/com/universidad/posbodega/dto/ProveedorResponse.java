package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProveedorResponse {

    private Long id;
    private String razonSocial;
    private String ruc;
    private String telefono;
    private String direccion;
    private String email;
    private Boolean estado;
}