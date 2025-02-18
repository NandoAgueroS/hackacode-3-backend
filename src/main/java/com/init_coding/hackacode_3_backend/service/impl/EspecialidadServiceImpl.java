package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.EspecialidadRequest;
import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.mapper.IEspecialidadMapper;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import com.init_coding.hackacode_3_backend.model.PaqueteServiciosEntity;
import com.init_coding.hackacode_3_backend.repository.IEspecialidadRepository;
import com.init_coding.hackacode_3_backend.service.IEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EspecialidadServiceImpl implements IEspecialidadService {

    @Autowired
    IEspecialidadRepository especialidadRepository;

    IEspecialidadMapper especialidadMapper = IEspecialidadMapper.mapper;

    @Override
    public List<EspecialidadResponse> findAll() {
        return especialidadMapper.
                toResponseList(
                        especialidadRepository.findAllByActivoTrue()
                );
    }

    @Override
    public List<EspecialidadResponse> findAllInactivas() {
        return especialidadMapper.
                toResponseList(
                        especialidadRepository.findAllByActivoFalse()
                );
    }

    @Override
    public EspecialidadResponse create(EspecialidadRequest especialidad) {
        EspecialidadEntity especialidadEntity = especialidadMapper.toEntity(especialidad);

        return especialidadMapper.toResponse(
                especialidadRepository.save(especialidadEntity)
        );
    }

    @Override
    public EspecialidadResponse update(Long especialidadId, EspecialidadRequest especialidad) throws ResourceNotFoundException {
        EspecialidadEntity especialidadExistente = especialidadRepository.findByIdAndActivoTrue(especialidadId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "Especialidad", especialidadId));
        EspecialidadEntity especialidadModificada = especialidadMapper.toEntity(especialidad);
        especialidadModificada.setId(especialidadId);

        especialidadRepository.save(especialidadModificada);

        return especialidadMapper.toResponse(especialidadModificada);
    }

    @Override
    public EspecialidadResponse findById(Long especialidadId) throws ResourceNotFoundException {
        EspecialidadEntity especialidad = especialidadRepository.findByIdAndActivoTrue(especialidadId).orElseThrow(()->
                new ResourceNotFoundException("buscar", "Especialidad", especialidadId));
        return especialidadMapper.toResponse(especialidad);
    }

    @Transactional
    @Override
    public void updateActivo(Long especialidadId, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!especialidadRepository.existsByIdAndActivoTrue(especialidadId))
                    throw new ResourceNotFoundException("eliminar", "Especialidad", especialidadId);
        }else{
            EspecialidadEntity especialidad = especialidadRepository.findById(especialidadId).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "Especialidad", especialidadId));
            if (especialidad.isActivo()) throw new EntityAlreadyActivaException("Especialidad", especialidadId);
        }
        especialidadRepository.updateActivoById(especialidadId, esActivo);
    }

    @Override
    public List<EspecialidadEntity> verificarEspecialidades(List<Long> especialidadesIDs) throws InvalidEspecialidadException {
        especialidadesIDs = new ArrayList<>(especialidadesIDs);
        if (especialidadesIDs == null || especialidadesIDs.isEmpty()) {
            throw new InvalidEspecialidadException("No se ingresó ninguna especialidad");
        }

        List<EspecialidadEntity> especialidadesRequest = especialidadRepository.findAllByIdInAndActivoTrue(especialidadesIDs);
        if (especialidadesRequest.isEmpty()){
            throw new InvalidEspecialidadException(especialidadesIDs);
        }

        if (especialidadesRequest.size() != especialidadesIDs.size()){
            List<Long> idsEncontrados = especialidadesRequest.stream().map(EspecialidadEntity::getId).toList();
            especialidadesIDs.removeAll(idsEncontrados);
            throw new InvalidEspecialidadException(especialidadesIDs);
        }

        return especialidadesRequest;
    }

    @Override
    public EspecialidadEntity verificarEspecialidades(Long especialidadId) throws InvalidEspecialidadException {
        EspecialidadEntity especialidadRequest = especialidadRepository.findByIdAndActivoTrue(especialidadId).orElseThrow(()->
                new InvalidEspecialidadException(especialidadId));

        return especialidadRequest;
    }
}
