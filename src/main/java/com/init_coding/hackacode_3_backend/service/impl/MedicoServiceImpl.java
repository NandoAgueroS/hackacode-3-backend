package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.mapper.MedicoMapper;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import com.init_coding.hackacode_3_backend.model.PaqueteServiciosEntity;
import com.init_coding.hackacode_3_backend.repository.IEspecialidadRepository;
import com.init_coding.hackacode_3_backend.repository.IMedicoRepository;
import com.init_coding.hackacode_3_backend.service.IEspecialidadService;
import com.init_coding.hackacode_3_backend.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        List<MedicoResponse> medicoResponses = medicoMapper.toResponseList(medicoRepository.findAllByActivoTrue());
        return medicoResponses;
    }

    @Override
    public List<MedicoResponse> findAllInactivos() {
        List<MedicoResponse> medicoResponses = medicoMapper.toResponseList(medicoRepository.findAllByActivoFalse());
        return medicoResponses;
    }

    @Override
    public List<MedicoResponse> findByEspecialidad(Long especialidadId) throws InvalidEspecialidadException {
        especialidadService.verificarEspecialidades(especialidadId);

        return medicoMapper.toResponseList(medicoRepository.findAllByEspecialidadIdAndActivoTrue(especialidadId));
    }

    @Override
    public MedicoResponse create(MedicoRequest medico) throws InvalidEspecialidadException {
        EspecialidadEntity especialidad = especialidadService.verificarEspecialidades(
                medico.getEspecialidadId());

        MedicoEntity medicoEntity = medicoMapper.toEntity(medico);
        //medicoEntity.setEspecialidades(especialidades);
        medicoRepository.save(medicoEntity);

        return medicoMapper.toResponse(medicoRepository.findById(medicoEntity.getId()).orElse(null));
    }

    @Override
    public MedicoResponse update(Long medicoId, MedicoRequest medico)
            throws ResourceNotFoundException, InvalidEspecialidadException {
        MedicoEntity medicoEntity = medicoRepository.findByIdAndActivoTrue(medicoId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "Medico", medicoId));

        EspecialidadEntity especialidad = especialidadService.verificarEspecialidades(
                medico.getEspecialidadId());

        MedicoEntity medicoModificado = medicoMapper.toEntity(medico);

        //medicoModificado.setEspecialidades(especialidadService.verificarEspecialidades(ids));
        medicoModificado.setId(medicoId);

        medicoRepository.save(medicoModificado);

        return medicoMapper.toResponse(medicoRepository.findByIdAndActivoTrue(medicoModificado.getId()).orElse(null));
    }

    @Override
    public MedicoResponse findById(Long medicoId) throws ResourceNotFoundException {
        return medicoMapper.toResponse(medicoRepository.findByIdAndActivoTrue(medicoId).orElseThrow(()->
                new ResourceNotFoundException("buscar", "Médico", medicoId)));
    }

    @Transactional
    @Override
    public void updateActivo(Long medicoId, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!medicoRepository.existsByIdAndActivoTrue(medicoId))
                throw new ResourceNotFoundException("eliminar", "Médico", medicoId);
        }else{
            MedicoEntity medico = medicoRepository.findById(medicoId).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "Médico", medicoId));
            if (medico.isActivo()) throw new EntityAlreadyActivaException("Médico", medicoId);
        }
        medicoRepository.updateActivoById(medicoId, esActivo);
    }

    @Override
    public boolean isValid(Long medicoId){
        return medicoRepository.existsByIdAndActivoTrue(medicoId);
    }
}
