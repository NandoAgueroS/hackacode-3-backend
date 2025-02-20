package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.PaqueteServiciosRequest;
import com.init_coding.hackacode_3_backend.dto.response.PaqueteServiciosResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IPaqueteServiciosService {

    /**
     * Obtiene todos los paquetes de servicios registrados.
     *
     * @return Lista de {@link PaqueteServiciosResponse} con la información de los paquetes de servicios.
     */
    List<PaqueteServiciosResponse> findAll();

    /**
     * Obtiene todos los paquetes de servicios registrados que contengan un determinado servicio individual.
     *
     * @param servicioIndividualId ID del servicio individual.
     * @return Lista de {@link PaqueteServiciosResponse} con la información de los paquetes de servicios.
     * @throws InvalidServicioException si el servicio individual no es válido.
     */
    List<PaqueteServiciosResponse> findByServicioIndividual(Long servicioIndividualId) throws InvalidServicioException;

    /**
     * Obtiene todos los paquetes de servicios inactivos.
     *
     * @return Lista de {@link PaqueteServiciosResponse} con la información de los paquetes de servicios.
     */
    List<PaqueteServiciosResponse> findAllInactivos();

    /**
     * Crea un paquete de servicios.
     *
     * @return {@link PaqueteServiciosResponse} con la información del paquete de servicios creado.
     * @param paqueteServicios Datos del paquete de servicios a guardar.
     * @throws InvalidServicioException si alguno de los servicios individuales del paquete no existe.
     */
    PaqueteServiciosResponse create(PaqueteServiciosRequest paqueteServicios) throws InvalidServicioException;

    /**
     * Actualiza los datos de un paquete de servicios existente.
     *
     * @param paqueteServiciosId ID del paquete de servicios
     * @param paqueteServicios Datos nuevos del paquete de servicios
     * @return {@link PaqueteServiciosResponse} con la información del paquete de servicios actualizado.
     * @throws ResourceNotFoundException si el paquete de servicios no existe.
     * @throws InvalidServicioException si alguno de los servicios individuales del paquete no existe.
     */
    PaqueteServiciosResponse update(Long paqueteServiciosId, PaqueteServiciosRequest paqueteServicios)
            throws ResourceNotFoundException, InvalidServicioException;

    /**
     * Busca un paquete de servicios por su ID.
     *
     * @return {@link PaqueteServiciosResponse} con la información del paquete de servicios.
     * @param paqueteServiciosId ID del paquete de servicios.
     * @throws ResourceNotFoundException si el paquete de servicios no existe.
     */
    PaqueteServiciosResponse findById(Long paqueteServiciosId)throws ResourceNotFoundException;

    /**
     * Permite activar o desactivar un paquete de servicios por su ID.
     *
     * @param paqueteServiciosId ID del paquete de servicios.
     * @param esActivo Valor actualizado para la propiedad activo.
     * @throws ResourceNotFoundException si el paquete de servicios no existe.
     * @throws EntityAlreadyActivaException si el paquete de servicios ya se encuentra activo.
     */
    void updateActivo(Long paqueteServiciosId, boolean esActivo)throws ResourceNotFoundException, EntityAlreadyActivaException;


    /**
     * Calcula el precio total de un paquete de servicios.
     *
     * @param serviciosIndividuales Servicios Individuales del paquete.
     * @return Precio del paquete de servicios.
     */
    BigDecimal calcularPrecio(List<ServicioIndividualEntity> serviciosIndividuales);
    
}
