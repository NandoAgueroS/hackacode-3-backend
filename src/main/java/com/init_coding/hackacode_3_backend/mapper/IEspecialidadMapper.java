package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.EspecialidadRequest;
import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IEspecialidadMapper {

    IEspecialidadMapper mapper = Mappers.getMapper(IEspecialidadMapper.class);

    EspecialidadRequest toRequest(EspecialidadEntity especialidad);

    EspecialidadResponse toResponse(EspecialidadEntity especialidad);

    EspecialidadEntity toEntity(EspecialidadResponse especialidad);

    EspecialidadEntity toEntity(EspecialidadRequest especialidad);

    @Mapping(target = "id")
    EspecialidadEntity toEntity(Long id);

    List<EspecialidadEntity> toEntityList(List<Long> ids);

    List<EspecialidadResponse> toResponseList(List<EspecialidadEntity> especialidades);

}
