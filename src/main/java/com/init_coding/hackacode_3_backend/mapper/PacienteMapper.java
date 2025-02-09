package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.PacienteRequest;
import com.init_coding.hackacode_3_backend.dto.response.PacienteResponse;
import com.init_coding.hackacode_3_backend.model.PacienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PacienteMapper {

    PacienteMapper mapper = Mappers.getMapper(PacienteMapper.class);

    PacienteResponse toResponse(PacienteEntity paciente);

    PacienteEntity toEntity(PacienteResponse paciente);

    PacienteEntity toEntity(PacienteRequest paciente);

    @Mapping(target = "id", source = "pacienteId")
    PacienteEntity toEntity(Long pacienteId);

    List<PacienteResponse> toResponseList(List<PacienteEntity> pacientes);

    List<PacienteEntity> toEntityList(List<PacienteRequest> pacientes);

}
