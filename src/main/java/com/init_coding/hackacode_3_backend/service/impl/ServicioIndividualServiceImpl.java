package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.ServicioIndividualRequest;
import com.init_coding.hackacode_3_backend.dto.response.ServicioIndividualResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.mapper.ServicioIndividualMapper;
import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;
import com.init_coding.hackacode_3_backend.repository.IServicioIndividualRepository;
import com.init_coding.hackacode_3_backend.service.IServicioIndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServicioIndividualServiceImpl implements IServicioIndividualService {

    @Autowired
    IServicioIndividualRepository servicioIndividualRepository;

    ServicioIndividualMapper servicioIndividualMapper = ServicioIndividualMapper.mapper;

    @Override
    public List<ServicioIndividualResponse> findAll() {
        return servicioIndividualMapper.toResponseList(
                servicioIndividualRepository.findAllByActivoTrue()
        );
    }

    @Override
    public List<ServicioIndividualResponse> findAllInactivos() {
        return servicioIndividualMapper.toResponseList(
                servicioIndividualRepository.findAllByActivoFalse()
        );
    }

    @Override
    public ServicioIndividualResponse create(ServicioIndividualRequest servicioIndividual) {
        return servicioIndividualMapper.toResponse(
                servicioIndividualRepository.save(
                        servicioIndividualMapper.toEntity(servicioIndividual)
                )
        );
    }

    @Override
    public ServicioIndividualResponse update(Long servicioIndividualId, ServicioIndividualRequest servicioIndividual) throws ResourceNotFoundException {
        ServicioIndividualEntity servicioIndividualExistente = servicioIndividualRepository.findByCodigoAndActivoTrue(servicioIndividualId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "ServicioIndividual", servicioIndividualId));

        ServicioIndividualEntity servicioIndividualModificado = servicioIndividualMapper.toEntity(servicioIndividual);
        servicioIndividualModificado.setCodigo(servicioIndividualId);

        return servicioIndividualMapper.toResponse(
                servicioIndividualRepository.save(servicioIndividualModificado)
        );
    }

    @Override
    public ServicioIndividualResponse findById(Long servicioIndividualId) throws ResourceNotFoundException {
        ServicioIndividualEntity servicioIndividualExistente = servicioIndividualRepository.findByCodigoAndActivoTrue(servicioIndividualId).orElseThrow(()->
                new ResourceNotFoundException("buscar", "ServicioIndividual", servicioIndividualId));
        return servicioIndividualMapper.toResponse(
                servicioIndividualExistente
        );
    }

    @Transactional
    @Override
    public void updateActivo(Long servicioIndividualId, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!servicioIndividualRepository.existsByCodigoAndActivoTrue(servicioIndividualId))
                throw new ResourceNotFoundException("eliminar", "ServicioIndividual", servicioIndividualId);
        }else{
            ServicioIndividualEntity servicioIndividual = servicioIndividualRepository.findById(servicioIndividualId).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "ServicioIndividual", servicioIndividualId));
            if (servicioIndividual.isActivo()) throw new EntityAlreadyActivaException("ServicioIndividual", servicioIndividualId);
        }
        servicioIndividualRepository.updateActivoById(servicioIndividualId, esActivo);
    }

    @Override
    public List<ServicioIndividualEntity> verificarServiciosIndividuales(List<Long> serviciosIndividualesIDs) throws InvalidServicioException {
        if (serviciosIndividualesIDs == null || serviciosIndividualesIDs.isEmpty()) {
            throw new InvalidServicioException("No se ingresó ninguna especialidad");
        }

        List<ServicioIndividualEntity> serviciosIndividualesExistentes = servicioIndividualRepository.findAllByCodigoInAndActivoTrue(serviciosIndividualesIDs);

        if (serviciosIndividualesExistentes.size() != serviciosIndividualesIDs.size()){
            List<Long> idsEncontrados = serviciosIndividualesExistentes.stream().map(ServicioIndividualEntity::getCodigo).toList();
            serviciosIndividualesIDs.removeAll(idsEncontrados);
            throw new InvalidServicioException(serviciosIndividualesIDs);
        }

        return serviciosIndividualesExistentes;
    }

    @Override
    public ServicioIndividualEntity verificarServicioIndividual(Long servicioIndividualId) throws InvalidServicioException {
        ServicioIndividualEntity servicioIndividualExistente = servicioIndividualRepository.findByCodigoAndActivoTrue(servicioIndividualId).orElseThrow(()->
                new InvalidServicioException(servicioIndividualId));

        return servicioIndividualExistente;
    }
}
