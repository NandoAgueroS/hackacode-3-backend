package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Table(name = "disponibilidad_medico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadMedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cubre_turno")
    private String cubreTurno;
    @Column(name = "hora_inicio")
    private LocalDate horaInicio;
    @Column(name = "hora_fin")
    private LocalDate horaFin;
    @Column(name = "dia_semana")
    private DayOfWeek diaSemana;
}
