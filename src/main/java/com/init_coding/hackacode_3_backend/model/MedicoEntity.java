package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "medico")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @OneToOne
    @JoinColumn(name = "disponibilidad_id", referencedColumnName = "id")
    private DisponibilidadMedicoEntity disponibilidad;
}
