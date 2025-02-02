package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "paciente")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PacienteEntity extends Persona{

    @Column(name = "tiene_obra_social")
    private boolean tieneObraSocial;

}
