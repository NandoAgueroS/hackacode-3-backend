package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.PacienteRequest;
import com.init_coding.hackacode_3_backend.dto.response.PacienteResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.mapper.PacienteMapper;
import com.init_coding.hackacode_3_backend.model.PacienteEntity;
import com.init_coding.hackacode_3_backend.repository.IPacienteRepository;
import com.init_coding.hackacode_3_backend.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    IPacienteRepository pacienteRepository;

    PacienteMapper pacienteMapper = PacienteMapper.mapper;

    @Override
    public List<PacienteResponse> findAll() {
        return pacienteMapper.toResponseList(
                pacienteRepository.findAllByActivoTrue()
        );
    }

    @Override
    public List<PacienteResponse> findAllInactivos() {
        return pacienteMapper.toResponseList(
                pacienteRepository.findAllByActivoFalse()
        );
    }

    @Override
    public PacienteResponse create(PacienteRequest paciente) {
        return pacienteMapper.toResponse(
                pacienteRepository.save(
                        pacienteMapper.toEntity(paciente)
                )
        );
    }

    @Override
    public PacienteResponse update(Long pacienteId, PacienteRequest paciente) throws ResourceNotFoundException{
        PacienteEntity pacienteExistente = pacienteRepository.findByIdAndActivoTrue(pacienteId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "Paciente", pacienteId));

        PacienteEntity pacienteModificado = pacienteMapper.toEntity(paciente);
        pacienteModificado.setId(pacienteId);

        return pacienteMapper.toResponse(
                pacienteRepository.save(pacienteModificado)
        );
    }

    @Override
    public PacienteResponse findById(Long pacienteId) throws ResourceNotFoundException {
        return pacienteMapper.toResponse(
                pacienteRepository
                        .findByIdAndActivoTrue(pacienteId)
                        .orElseThrow(()->
                                new ResourceNotFoundException("buscar", "Paciente", pacienteId)));
    }

    @Override
    public PacienteResponse findByDni(String pacienteDni) throws ResourceNotFoundException {
        return pacienteMapper.toResponse(
                pacienteRepository
                        .findByDniAndActivoTrue(pacienteDni)
                        .orElseThrow(()->
                                new ResourceNotFoundException("buscar", "Paciente", pacienteDni)));
    }

    @Transactional
    @Override
    public void updateActivo(Long pacienteId, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!pacienteRepository.existsByIdAndActivoTrue(pacienteId))
                    throw new ResourceNotFoundException("eliminar", "Paciente", pacienteId);
        }else{
            PacienteEntity paciente = pacienteRepository.findById(pacienteId).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "Paciente", pacienteId));
            if (paciente.isActivo()) throw new EntityAlreadyActivaException("Paciente", pacienteId);
        }
        pacienteRepository.updateActivoById(pacienteId, esActivo);

    }

    @Override
    public boolean isValid(Long pacienteId){
        return pacienteRepository.existsByIdAndActivoTrue(pacienteId);
    }
}
