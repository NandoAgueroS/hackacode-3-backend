package com.init_coding.hackacode_3_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicoResponse extends PersonaResponse{

    private BigDecimal sueldo;

    private List<DisponibilidadResponse> disponibilidades;

    private EspecialidadResponse especialidad;

}
