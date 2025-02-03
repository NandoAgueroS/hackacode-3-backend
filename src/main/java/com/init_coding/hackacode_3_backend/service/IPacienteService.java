package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.PacienteRequest;
import com.init_coding.hackacode_3_backend.dto.response.PacienteResponse;
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
     * Elimina un paciente por su ID.
     *
     * @param pacienteId ID del paciente.
     * @throws ResourceNotFoundException si el paciente no existe.
     */
    void delete(Long pacienteId)throws ResourceNotFoundException;
    
}
