package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.PacienteRequest;
import com.init_coding.hackacode_3_backend.dto.response.PacienteResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.mapper.PacienteMapper;
import com.init_coding.hackacode_3_backend.model.PacienteEntity;
import com.init_coding.hackacode_3_backend.repository.IPacienteRepository;
import com.init_coding.hackacode_3_backend.service.IPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteServiceImpl implements IPacienteService {

    @Autowired
    IPacienteRepository pacienteRepository;

    PacienteMapper pacienteMapper = PacienteMapper.mapper;

    @Override
    public List<PacienteResponse> findAll() {
        return pacienteMapper.toResponseList(
                pacienteRepository.findAll()
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
        PacienteEntity pacienteExistente = pacienteRepository.findById(pacienteId).orElseThrow(()->
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
                        .findById(pacienteId)
                        .orElseThrow(()->
                                new ResourceNotFoundException("buscar", "Paciente", pacienteId)));
    }

    @Override
    public void delete(Long pacienteId) throws ResourceNotFoundException {
        pacienteRepository.findById(pacienteId).orElseThrow(()->
                new ResourceNotFoundException("eliminar", "Paciente", pacienteId));

        pacienteRepository.deleteById(pacienteId);
    }
}
