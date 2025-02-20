package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.PaqueteServiciosRequest;
import com.init_coding.hackacode_3_backend.dto.response.PaqueteServiciosResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.mapper.PaqueteServiciosMapper;
import com.init_coding.hackacode_3_backend.model.PaqueteServiciosEntity;
import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;
import com.init_coding.hackacode_3_backend.repository.IPaqueteServiciosRepository;
import com.init_coding.hackacode_3_backend.service.IPaqueteServiciosService;
import com.init_coding.hackacode_3_backend.service.IServicioIndividualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaqueteServiciosServiceImpl implements IPaqueteServiciosService {

    @Autowired
    IPaqueteServiciosRepository paqueteServiciosRepository;

    @Autowired
    IServicioIndividualService servicioIndividualService;

    PaqueteServiciosMapper paqueteServiciosMapper = PaqueteServiciosMapper.mapper;

    @Override
    public List<PaqueteServiciosResponse> findAll() {
        return paqueteServiciosMapper.toResponseList(
                paqueteServiciosRepository.findAllByActivoTrue()
        );
    }

    @Override
    public List<PaqueteServiciosResponse> findByServicioIndividual(Long servicioIndividualId) throws InvalidServicioException {
        servicioIndividualService.verificarServicioIndividual(servicioIndividualId);

        return paqueteServiciosMapper.toResponseList(
                paqueteServiciosRepository.findAllByServicios_CodigoAndActivoTrue(servicioIndividualId)
        );
    }

    @Override
    public List<PaqueteServiciosResponse> findAllInactivos() {
        return paqueteServiciosMapper.toResponseList(
                paqueteServiciosRepository.findAllByActivoFalse()
        );
    }

    @Override
    public PaqueteServiciosResponse create(PaqueteServiciosRequest paqueteServicios) throws InvalidServicioException {
        List<ServicioIndividualEntity> serviciosIndividualesExistentes =
                servicioIndividualService.verificarServiciosIndividuales(
                        paqueteServicios.getServicios()
                );

        PaqueteServiciosEntity paqueteServiciosNuevo = paqueteServiciosMapper.toEntity(paqueteServicios);

        paqueteServiciosNuevo.setServicios(serviciosIndividualesExistentes);
        paqueteServiciosNuevo.setPrecio(
                this.calcularPrecio(serviciosIndividualesExistentes)
        );

        paqueteServiciosRepository.save(paqueteServiciosNuevo);

        return paqueteServiciosMapper.toResponse(paqueteServiciosNuevo);
    }

    @Override
    public PaqueteServiciosResponse update(Long paqueteServiciosId,
                                           PaqueteServiciosRequest paqueteServicios) throws ResourceNotFoundException,
                                                                                            InvalidServicioException {
        PaqueteServiciosEntity paqueteServiciosExistente = paqueteServiciosRepository.findByCodigoAndActivoTrue(paqueteServiciosId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "PaqueteServicios", paqueteServiciosId));

        PaqueteServiciosEntity paqueteServiciosModificado = paqueteServiciosMapper.toEntity(paqueteServicios);

        List<ServicioIndividualEntity> serviciosIndividualesExistentes =
                servicioIndividualService.verificarServiciosIndividuales(
                        paqueteServicios.getServicios()
                );
        paqueteServiciosModificado.setServicios(serviciosIndividualesExistentes);
        paqueteServiciosModificado.setPrecio(
                this.calcularPrecio(serviciosIndividualesExistentes)
        );
        paqueteServiciosModificado.setCodigo(paqueteServiciosExistente.getCodigo());

        return paqueteServiciosMapper.toResponse(
                paqueteServiciosRepository.save(paqueteServiciosModificado)
        );
    }

    @Override
    public PaqueteServiciosResponse findById(Long paqueteServiciosId) throws ResourceNotFoundException {
        return paqueteServiciosMapper.toResponse(
                paqueteServiciosRepository
                        .findByCodigoAndActivoTrue(paqueteServiciosId)
                        .orElseThrow(()->
                                new ResourceNotFoundException("buscar", "PaqueteServicios", paqueteServiciosId)));
    }

    @Transactional
    @Override
    public void updateActivo(Long paqueteServiciosId, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!paqueteServiciosRepository.existsByCodigoAndActivoTrue(paqueteServiciosId))
                    throw new ResourceNotFoundException("eliminar", "PaqueteServicios", paqueteServiciosId);
        }else{
            PaqueteServiciosEntity paqueteServicios = paqueteServiciosRepository.findById(paqueteServiciosId).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "PaqueteServicios", paqueteServiciosId));
            if (paqueteServicios.isActivo()) throw new EntityAlreadyActivaException("PaqueteServicios", paqueteServiciosId);
        }

        paqueteServiciosRepository.updateActivoById(paqueteServiciosId, esActivo);
    }

    @Override
    public BigDecimal calcularPrecio(List<ServicioIndividualEntity> serviciosIndividuales) {
        BigDecimal sumaPrecios = serviciosIndividuales.stream()
                .map(ServicioIndividualEntity::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal descuento = new BigDecimal("0.15");

        return sumaPrecios.subtract(sumaPrecios.multiply(descuento));
    }

}
