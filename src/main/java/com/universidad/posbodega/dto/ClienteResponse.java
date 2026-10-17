package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClienteResponse {

    private Long id;
    private String nombres;
    private String documento;
    private String telefono;
    private String direccion;
    private Boolean estado;
}