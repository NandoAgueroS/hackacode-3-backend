package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.ServicioIndividualRequest;
import com.init_coding.hackacode_3_backend.dto.response.ServicioIndividualResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;

import java.util.List;

public interface IServicioIndividualService {

    /**
     * Obtiene todos los servicios individuales registrados.
     *
     * @return Lista de {@link ServicioIndividualResponse} con la información de los servicios individuales.
     */
    List<ServicioIndividualResponse> findAll();

    /**
     * Obtiene todos los servicios individuales inactivos.
     *
     * @return Lista de {@link ServicioIndividualResponse} con la información de los servicios individuales.
     */
    List<ServicioIndividualResponse> findAllInactivos();

    /**
     * Crea un servicio individual.
     *
     * @return {@link ServicioIndividualResponse} con la información del servicio individual creado.
     * @param servicioIndividual Datos del servicio individual a guardar.
     */
    ServicioIndividualResponse create(ServicioIndividualRequest servicioIndividual);

    /**
     * Actualiza los datos de un servicio individual existente.
     *
     * @param servicioIndividualId ID del servicio individual
     * @param servicioIndividual Datos nuevos del servicio individual
     * @return {@link ServicioIndividualResponse} con la información del servicio individual actualizado.
     * @throws ResourceNotFoundException si el servicio individual no existe.
     */
    ServicioIndividualResponse update(Long servicioIndividualId, ServicioIndividualRequest servicioIndividual)throws ResourceNotFoundException;

    /**
     * Busca un servicio individual por su ID.
     *
     * @return {@link ServicioIndividualResponse} con la información del servicio individual.
     * @param servicioIndividualId ID del servicio individual.
     * @throws ResourceNotFoundException si el servicio individual no existe.
     */
    ServicioIndividualResponse findById(Long servicioIndividualId)throws ResourceNotFoundException;

    /**
     * Permite activar o desactivar un servicio individual por su ID.
     *
     * @param servicioIndividualId ID del servicio individual.
     * @param esActivo Valor actualizado para la propiedad activo.
     * @throws ResourceNotFoundException si el servicio individual no existe.
     * @throws EntityAlreadyActivaException si el servicio individual ya se encuentra activo.
     */
    void updateActivo(Long servicioIndividualId, boolean esActivo)throws ResourceNotFoundException, EntityAlreadyActivaException;

    /**
     * Verifica que existan los servicios individuales ingresados.
     *
     * @return {@link List<com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity>} con la información de los servicios.
     * @param serviciosIndividualesIDs IDs de los servicios individuales.
     * @throws com.init_coding.hackacode_3_backend.exception.InvalidServicioException si algun servicio individual no existe.
     */
    List<ServicioIndividualEntity> verificarServiciosIndividuales(List<Long> serviciosIndividualesIDs)throws InvalidServicioException;

    /**
     * Verifica que exista un servicio individual.
     *
     * @param servicioIndividualId IDs de los servicios individuales.
     * @return {@link com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity} con la información del servicio individual.
     * @throws InvalidServicioException si algun servicio individual no existe.
     */
    ServicioIndividualEntity verificarServicioIndividual(Long servicioIndividualId) throws InvalidServicioException;
    
}
