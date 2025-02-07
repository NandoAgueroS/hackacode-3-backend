package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "medicos_especialidades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoEspecialidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    @ManyToOne
    @JoinColumn(name = "especialidad_id")
    private EspecialidadEntity especialidad;

    @OneToMany(mappedBy = "medicoEspecialidad", cascade = CascadeType.ALL)
    private List<DisponibilidadEntity> disponibilidades;

}
