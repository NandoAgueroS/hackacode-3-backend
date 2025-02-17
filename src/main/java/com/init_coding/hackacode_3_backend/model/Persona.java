package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String apellido;

    private String dni;

    private LocalDate fechaNac;

    private String email;

    private String telefono;

    private String direccion;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;
}
