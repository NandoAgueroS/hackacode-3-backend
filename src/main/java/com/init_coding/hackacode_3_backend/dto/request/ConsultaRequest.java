package com.init_coding.hackacode_3_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaRequest {

    private Long pacienteId;

    private Long medicoId;

    private Long servicioMedicoCodigo;

    private LocalDate fecha;

    private LocalTime hora;

    private String estado;

}
