package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.mapper.MedicoMapper;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEspecialidadEntity;
import com.init_coding.hackacode_3_backend.repository.IEspecialidadRepository;
import com.init_coding.hackacode_3_backend.repository.IMedicoRepository;
import com.init_coding.hackacode_3_backend.service.IEspecialidadService;
import com.init_coding.hackacode_3_backend.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicoServiceImpl implements IMedicoService {

    @Autowired
    IMedicoRepository medicoRepository;

    @Autowired
    IEspecialidadRepository especialidadRepository;
    
    @Autowired
    IEspecialidadService especialidadService;

    MedicoMapper medicoMapper = MedicoMapper.mapper;

    @Override
    public List<MedicoResponse> findAll() {
        List<MedicoResponse> medicoResponses = medicoMapper.toResponseList(medicoRepository.findAll());
        return medicoResponses;
    }

    @Override
    public List<MedicoResponse> findByEspecialidad(Long especialidadId) throws InvalidEspecialidadException {
        especialidadService.verificarEspecialidades(especialidadId);

        return medicoMapper.toResponseList(medicoRepository.findAllByEspecialidadesId(especialidadId));
    }

    @Override
    public MedicoResponse create(MedicoRequest medico) throws InvalidEspecialidadException {
        List<EspecialidadEntity> especialidades = especialidadService.verificarEspecialidades(
                medico.getEspecialidades().stream()
                        .map(medicoEspecialidad -> medicoEspecialidad.getEspecialidad())
                        .toList());

        MedicoEntity medicoEntity = medicoMapper.toEntity(medico);
        //medicoEntity.setEspecialidades(especialidades);
        medicoRepository.save(medicoEntity);

        return medicoMapper.toResponse(medicoRepository.findById(medicoEntity.getId()).orElse(null));
    }

    @Override
    public MedicoResponse update(Long medicoId, MedicoRequest medico)
            throws ResourceNotFoundException, InvalidEspecialidadException {
        MedicoEntity medicoEntity = medicoRepository.findById(medicoId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "Medico", medicoId));

        List<EspecialidadEntity> especialidades = especialidadService.verificarEspecialidades(
                medico.getEspecialidades().stream()
                        .map(medicoEspecialidad -> medicoEspecialidad.getEspecialidad())
                        .toList());

        MedicoEntity medicoModificado = medicoMapper.toEntity(medico);

        //medicoModificado.setEspecialidades(especialidadService.verificarEspecialidades(ids));
        medicoModificado.setId(medicoId);

        medicoRepository.save(medicoModificado);

        return medicoMapper.toResponse(medicoRepository.findById(medicoModificado.getId()).orElse(null));
    }

    @Override
    public MedicoResponse findById(Long medicoId) throws ResourceNotFoundException {
        return medicoMapper.toResponse(medicoRepository.findById(medicoId).orElseThrow(()->
                new ResourceNotFoundException("buscar", "Médico", medicoId)));
    }

    @Override
    public void delete(Long medicoId) throws ResourceNotFoundException {
        medicoRepository.findById(medicoId).orElseThrow(()->
                new ResourceNotFoundException("eliminar", "Médico", medicoId));
        medicoRepository.deleteById(medicoId);
    }

}
