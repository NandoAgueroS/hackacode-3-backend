package com.init_coding.hackacode_3_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicoEspecialidadRequest {

    private Long especialidad;

    private List<DisponibilidadRequest> disponibilidades;

}
