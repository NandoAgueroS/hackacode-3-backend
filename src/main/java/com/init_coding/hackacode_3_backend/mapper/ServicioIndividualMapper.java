package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.ServicioIndividualRequest;
import com.init_coding.hackacode_3_backend.dto.response.ServicioIndividualResponse;
import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ServicioIndividualMapper {

    ServicioIndividualMapper mapper = Mappers.getMapper(ServicioIndividualMapper.class);

    ServicioIndividualEntity toEntity(ServicioIndividualResponse servicio);

    ServicioIndividualEntity toEntity(ServicioIndividualRequest servicio);

    ServicioIndividualResponse toResponse(ServicioIndividualEntity servicio);

    List<ServicioIndividualEntity> toEntityList(List<ServicioIndividualRequest> servicio);

    List<ServicioIndividualResponse> toResponseList(List<ServicioIndividualEntity> servicio);

    @Mapping(target = "codigo", source = "id")
    ServicioIndividualEntity idToEntity(Long id);

    List<ServicioIndividualEntity> idsToEntityList(List<Long> ids);


}
