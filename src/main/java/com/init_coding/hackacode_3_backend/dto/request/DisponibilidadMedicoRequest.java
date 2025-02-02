package com.init_coding.hackacode_3_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DisponibilidadMedicoRequest {

    private Long id;

    private String cubreTurno;

    @Schema(type = "String", pattern = "HH:mm:SS")
    private LocalTime horaInicio;

    @Schema(type = "String", pattern = "HH:mm:SS")
    private LocalTime horaFin;

    private DayOfWeek diaSemana;

}
