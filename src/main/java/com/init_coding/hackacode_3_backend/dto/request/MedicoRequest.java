package com.init_coding.hackacode_3_backend.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicoRequest extends PersonaRequest{

    private BigDecimal sueldo;

    private List<MedicoEspecialidadRequest> especialidades;

}
