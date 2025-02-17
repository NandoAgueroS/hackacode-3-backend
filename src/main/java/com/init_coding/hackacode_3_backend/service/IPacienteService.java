package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.PacienteRequest;
import com.init_coding.hackacode_3_backend.dto.response.PacienteResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {

    /**
     * Obtiene todos los pacientes registrados.
     *
     * @return Lista de {@link PacienteResponse} con la información de los pacientes.
     */
    List<PacienteResponse> findAll();

    /**
     * Obtiene todos los pacientes inactivos.
     *
     * @return Lista de {@link PacienteResponse} con la información de los pacientes.
     */
    List<PacienteResponse> findAllInactivos();

    /**
     * Crea un paciente.
     *
     * @return {@link PacienteResponse} con la información del paciente creado.
     * @param paciente Datos del paciente a guardar.
     */
    PacienteResponse create(PacienteRequest paciente);

    /**
     * Actualiza los datos de un paciente existente.
     *
     * @return {@link PacienteResponse} con la información del paciente actualizado.
     * @throws ResourceNotFoundException si el paciente no existe.
     */
    PacienteResponse update(Long pacienteId, PacienteRequest paciente)throws ResourceNotFoundException;

    /**
     * Busca un paciente por su ID.
     *
     * @return {@link PacienteResponse} con la información del paciente.
     * @param pacienteId ID del paciente.
     * @throws ResourceNotFoundException si el paciente no existe.
     */
    PacienteResponse findById(Long pacienteId)throws ResourceNotFoundException;

    /**
     * Permite activar o desactivar un paciente por su ID.
     *
     * @param pacienteId ID del paciente.
     * @param esActivo Valor actualizado para la propiedad activo.
     * @throws ResourceNotFoundException si el paciente no existe.
     * @throws EntityAlreadyActivaException si el paciente ya se encuentra activo.
     */
    void updateActivo(Long pacienteId, boolean esActivo)throws ResourceNotFoundException, EntityAlreadyActivaException;

    /**
     * Verifica si el paciente es válido.
     *
     * @param pacienteId ID del paciente.
     * @return boolean indicando si el paciente existe o no.
     */
    boolean isValid(Long pacienteId);

}
