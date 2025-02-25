package com.init_coding.hackacode_3_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaResponse {

    private Long codigo;

    private Long pacienteId;

    private Long medicoId;

    private ServicioMedicoResponse servicioMedico;

    private LocalDate fecha;

    private LocalTime hora;

    private String estado;

    private boolean activo;

    private PagoResponse pago;

}
