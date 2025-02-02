package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especialidad")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EspecialidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

}
