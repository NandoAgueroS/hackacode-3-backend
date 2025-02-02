package com.init_coding.hackacode_3_backend.dto.response;

import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import com.init_coding.hackacode_3_backend.model.DisponibilidadMedicoEntity;
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

    private List<EspecialidadResponse> especialidades;

    private BigDecimal sueldo;

    private List<DisponibilidadMedicoResponse> disponibilidades;

}
