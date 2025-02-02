package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.DisponibilidadMedicoRequest;
import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.DisponibilidadMedicoResponse;
import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.model.DisponibilidadMedicoEntity;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = IEspecialidadMapper.class)
public interface MedicoMapper {

    MedicoMapper mapper = Mappers.getMapper(MedicoMapper.class);

    @Mapping(target = "especialidades", source = "especialidadesIDs")
    MedicoEntity toEntity(MedicoRequest medico);

    MedicoEntity toEntity(MedicoResponse medico);

    MedicoResponse toResponse(MedicoEntity medico);

    List<MedicoResponse> toResponseList(List<MedicoEntity> medicos);

    DisponibilidadMedicoEntity toDisponibilidadMedicoEntity(DisponibilidadMedicoRequest disponibilidad);
    DisponibilidadMedicoEntity toDisponibilidadMedicoEntity(DisponibilidadMedicoResponse disponibilidad);
    DisponibilidadMedicoResponse toDisponibilidadMedicoResponse(DisponibilidadMedicoEntity disponibilidad);

    List<DisponibilidadMedicoResponse> toDisponibilidadMedicoResponseList(List<DisponibilidadMedicoEntity> disponibilidades);
    List<DisponibilidadMedicoEntity> toDisponibilidadMedicoEntityList(List<DisponibilidadMedicoRequest> disponibilidades);
}
