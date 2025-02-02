package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "consulta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private PacienteEntity paciente;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private MedicoEntity medico;

    @OneToOne
    @JoinColumn(name = "servicio_medico_id", referencedColumnName = "codigo")
    private ServicioMedico servicioMedico;

    private LocalDate fecha;

    private LocalTime hora;

    private String estado;

    @OneToOne
    @JoinColumn(name = "codigo_pago", referencedColumnName = "codigo")
    private PagoEntity pago;

}
