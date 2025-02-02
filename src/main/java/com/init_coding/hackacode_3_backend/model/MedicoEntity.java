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

    @ManyToMany
    @JoinTable(
            name = "medico_especialidad",
            joinColumns = @JoinColumn(name = "medico_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidad_id")
    )
    private List<EspecialidadEntity> especialidades;


    @OneToMany(mappedBy = "medico")
    private List<ConsultaEntity> consultas;

    private BigDecimal sueldo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "medico_id", referencedColumnName = "id")
    private List<DisponibilidadMedicoEntity> disponibilidades;

}
