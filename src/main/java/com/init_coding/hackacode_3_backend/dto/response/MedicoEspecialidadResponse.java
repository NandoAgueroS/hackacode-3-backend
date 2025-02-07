package com.init_coding.hackacode_3_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoEspecialidadResponse {

    private Long id;

    private EspecialidadResponse especialidad;

    private List<DisponibilidadResponse> disponibilidades;

}
