package com.universidad.posbodega.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Size(max = 150)
    private String nombres;

    @Size(max = 20)
    private String documento;

    @Size(max = 20)
    private String telefono;

    @Size(max = 255)
    private String direccion;
}