package com.init_coding.hackacode_3_backend.dto.request;

import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import com.init_coding.hackacode_3_backend.model.DisponibilidadMedicoEntity;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MedicoRequest extends PersonaRequest{

    private List<Long> especialidadesIDs;

    private BigDecimal sueldo;

    private List<DisponibilidadMedicoRequest> disponibilidades;

}
