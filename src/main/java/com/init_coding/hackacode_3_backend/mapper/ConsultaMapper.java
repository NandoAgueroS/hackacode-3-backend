package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.ConsultaRequest;
import com.init_coding.hackacode_3_backend.dto.response.ConsultaResponse;
import com.init_coding.hackacode_3_backend.dto.response.PagoResponse;
import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import com.init_coding.hackacode_3_backend.model.PagoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {
        PaqueteServiciosMapper.class,
        ServicioIndividualMapper.class,
        MedicoMapper.class,
        PacienteMapper.class})

public interface ConsultaMapper {

    ConsultaMapper mapper = Mappers.getMapper(ConsultaMapper.class);

    @Mapping(target = "medicoId", source = "medico.id")
    @Mapping(target = "pacienteId", source = "paciente.id")
    ConsultaResponse toResponse(ConsultaEntity consulta);

    @Mapping(target = "medico.id", source = "medicoId")
    @Mapping(target = "paciente.id", source = "pacienteId")
    //@Mapping(target = "servicioMedico", source = "servicioMedicoCodigo")
    ConsultaEntity toEntity(ConsultaRequest consulta);

    List<ConsultaResponse> toResponseList(List<ConsultaEntity> consultas);

    List<ConsultaEntity> toEntityList(List<ConsultaRequest> consultas);

    PagoResponse pagoEntityToPagoResponse(PagoEntity pagoEntity);


}
