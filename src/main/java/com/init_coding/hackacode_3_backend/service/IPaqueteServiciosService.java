package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.PaqueteServiciosRequest;
import com.init_coding.hackacode_3_backend.dto.response.PaqueteServiciosResponse;
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
     * Elimina un paquete de servicios por su ID.
     *
     * @param paqueteServiciosId ID del paquete de servicios.
     * @throws ResourceNotFoundException si el paquete de servicios no existe.
     */
    void delete(Long paqueteServiciosId)throws ResourceNotFoundException;


    /**
     * Calcula el precio total de un paquete de servicios.
     *
     * @param serviciosIndividuales Servicios Individuales del paquete.
     * @return Precio del paquete de servicios.
     */
    BigDecimal calcularPrecio(List<ServicioIndividualEntity> serviciosIndividuales);
    
}
