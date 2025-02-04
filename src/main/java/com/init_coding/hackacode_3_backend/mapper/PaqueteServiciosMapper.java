package com.init_coding.hackacode_3_backend.mapper;

import com.init_coding.hackacode_3_backend.dto.request.PaqueteServiciosRequest;
import com.init_coding.hackacode_3_backend.dto.response.PaqueteServiciosResponse;
import com.init_coding.hackacode_3_backend.model.PaqueteServiciosEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = ServicioIndividualMapper.class)
public interface PaqueteServiciosMapper {

    PaqueteServiciosMapper mapper = Mappers.getMapper(PaqueteServiciosMapper.class);

    PaqueteServiciosEntity toEntity(PaqueteServiciosRequest paqueteServicios);

    PaqueteServiciosEntity toEntity(PaqueteServiciosResponse paqueteServicios);

    PaqueteServiciosResponse toResponse(PaqueteServiciosEntity paqueteServicios);

    @Mapping(target = "servicios", source = "serviciosIDs")
    List<PaqueteServiciosEntity> toEntityList(List<PaqueteServiciosRequest> paqueteServicios);

    @Mapping(target = "serviciosIDs", source = "servicios")
    List<PaqueteServiciosResponse> toResponseList(List<PaqueteServiciosEntity> paqueteServicios);


}
