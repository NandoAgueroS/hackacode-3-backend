package com.init_coding.hackacode_3_backend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @Schema(type = "String", pattern = "HH:mm:SS")
    private LocalTime hora;

    private String estado;

    private String metodoPago;

    private boolean esPagado;

}
