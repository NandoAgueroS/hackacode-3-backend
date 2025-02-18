package com.init_coding.hackacode_3_backend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class TurnoDisponibleResponse {

    private LocalDate fecha;

    private LocalTime hora;

    private int duracion;

    private Long medicoId;

}
