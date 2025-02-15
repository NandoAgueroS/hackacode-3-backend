package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.DisponibilidadRequest;
import com.init_coding.hackacode_3_backend.dto.request.EspecialidadRequest;
import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.DisponibilidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.model.DisponibilidadEntity;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = IEspecialidadMapper.class)
public interface MedicoMapper {

    MedicoMapper mapper = Mappers.getMapper(MedicoMapper.class);

    @Mapping(target = "especialidad.id", source = "especialidadId")
    MedicoEntity toEntity(MedicoRequest medico);

    @Mapping(target = "id", source = "medicoId")
    MedicoEntity toEntity(Long medicoId);


    MedicoResponse toResponse(MedicoEntity medico);

    List<MedicoResponse> toResponseList(List<MedicoEntity> medicos);

    DisponibilidadEntity toDisponibilidadMedicoEntity(DisponibilidadRequest disponibilidad);
    DisponibilidadEntity toDisponibilidadMedicoEntity(DisponibilidadResponse disponibilidad);
    DisponibilidadResponse toDisponibilidadMedicoResponse(DisponibilidadEntity disponibilidad);

    List<DisponibilidadResponse> toDisponibilidadMedicoResponseList(List<DisponibilidadEntity> disponibilidades);
    List<DisponibilidadEntity> toDisponibilidadMedicoEntityList(List<DisponibilidadRequest> disponibilidades);

}
