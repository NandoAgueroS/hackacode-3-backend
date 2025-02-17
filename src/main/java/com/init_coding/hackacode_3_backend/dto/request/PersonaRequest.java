package com.init_coding.hackacode_3_backend.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class PersonaRequest {

    private String nombre;

    private String apellido;

    private String dni;

    private LocalDate fechaNac;

    private String email;

    private String telefono;

    private String direccion;

}
