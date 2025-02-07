package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.DisponibilidadRequest;
import com.init_coding.hackacode_3_backend.dto.request.MedicoEspecialidadRequest;
import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.DisponibilidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.MedicoEspecialidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.model.DisponibilidadEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEspecialidadEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = IEspecialidadMapper.class)
public interface MedicoMapper {

    MedicoMapper mapper = Mappers.getMapper(MedicoMapper.class);

    //@Mapping(target = "especialidades", source = "especialidadesIDs")
    MedicoEntity toEntity(MedicoRequest medico);

    @Mapping(target = "id")
    MedicoEntity toEntity(Long id);

    MedicoEntity toEntity(MedicoResponse medico);

    MedicoResponse toResponse(MedicoEntity medico);

    @AfterMapping
    default void setRelacionEspecialidadYDisponibilidad(@MappingTarget MedicoEntity medico){
        if (medico.getEspecialidades() != null){
            for (MedicoEspecialidadEntity especialidad : medico.getEspecialidades()){
                especialidad.setMedico(medico);
                if (especialidad.getDisponibilidades() != null){
                    for (DisponibilidadEntity disponibilidad : especialidad.getDisponibilidades()){
                        disponibilidad.setMedicoEspecialidad(especialidad);
                    }
                }
            }
        }
    }

    List<MedicoResponse> toResponseList(List<MedicoEntity> medicos);

    DisponibilidadEntity toDisponibilidadMedicoEntity(DisponibilidadRequest disponibilidad);
    DisponibilidadEntity toDisponibilidadMedicoEntity(DisponibilidadResponse disponibilidad);
    DisponibilidadResponse toDisponibilidadMedicoResponse(DisponibilidadEntity disponibilidad);

    List<DisponibilidadResponse> toDisponibilidadMedicoResponseList(List<DisponibilidadEntity> disponibilidades);
    List<DisponibilidadEntity> toDisponibilidadMedicoEntityList(List<DisponibilidadRequest> disponibilidades);

    MedicoEspecialidadEntity toMedicoEspecialidadEntity(MedicoEspecialidadRequest medicoEspecialidad);
    MedicoEspecialidadEntity toMedicoEspecialidadEntity(MedicoEspecialidadResponse medicoEspecialidad);
    MedicoEspecialidadResponse toMedicoEspecialidadResponse(MedicoEspecialidadEntity medicoEspecialidad);

    List<MedicoEspecialidadResponse> toMedicoEspecialidadResponseList(List<MedicoEspecialidadEntity> medicoEspecialidades);
    List<MedicoEspecialidadEntity> toMedicoEspecialidadEntityList(List<MedicoEspecialidadRequest> medicoEspecialidades);
}
