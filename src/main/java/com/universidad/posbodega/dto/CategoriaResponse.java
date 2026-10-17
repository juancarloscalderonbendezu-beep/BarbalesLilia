package com.universidad.posbodega.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoriaResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private Boolean estado;
}