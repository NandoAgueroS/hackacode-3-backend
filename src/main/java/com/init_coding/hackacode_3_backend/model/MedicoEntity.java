package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "medico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MedicoEntity extends Persona{

    @OneToMany(mappedBy = "medico")
    private List<ConsultaEntity> consultas;

    private BigDecimal sueldo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "medico")
    private List<MedicoEspecialidadEntity> especialidades;

}
