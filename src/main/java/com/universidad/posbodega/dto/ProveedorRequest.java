package com.universidad.posbodega.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorRequest {

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 150)
    private String razonSocial;

    @Size(max = 20)
    private String ruc;

    @Size(max = 20)
    private String telefono;

    @Size(max = 255)
    private String direccion;

    @Email(message = "El email no tiene un formato válido")
    @Size(max = 150)
    private String email;
}