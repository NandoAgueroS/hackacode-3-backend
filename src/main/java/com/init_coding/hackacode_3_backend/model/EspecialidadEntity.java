package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especialidad")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EspecialidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean activo = true;

}
