package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.EspecialidadRequest;
import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;

import java.util.List;

public interface IEspecialidadService {

    /**
     * Obtiene todos las especialidades registrados.
     *
     * @return Lista de {@link com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse} con la información de las especialidades.
     */
    List<EspecialidadResponse> findAll();

    /**
     * Crea una especialidad.
     *
     * @return {@link EspecialidadResponse} con la información de la especialidad creada.
     * @param especialidad Datos de la especialidad a guardar.
     */
    EspecialidadResponse create(EspecialidadRequest especialidad);

    /**
     * Modifica los datos de una especialidad existente.
     *
     * @param especialidadId ID de la especialidad.
     * @param especialidad Datos de la especialidad modificada.
     * @return {@link EspecialidadResponse} con la información de la especialidad modificada.
     * @throws ResourceNotFoundException si la especialidad no existe.
     */
    EspecialidadResponse update(Long especialidadId, EspecialidadRequest especialidad)throws ResourceNotFoundException;

    /**
     * Busca una especialidad por su ID.
     *
     * @return {@link EspecialidadResponse} con la información de la especialidad.
     * @param especialidadId ID de la especialidad.
     * @throws ResourceNotFoundException si la especialidad no existe.
     */
    EspecialidadResponse findById(Long especialidadId)throws ResourceNotFoundException;

    /**
     * Elimina una especialidad por su ID.
     *
     * @param especialidadId ID de la especialidad.
     * @throws ResourceNotFoundException si la especialidad no existe.
     */
    void delete(Long especialidadId)throws ResourceNotFoundException;

    /**
     * Verifica que existan las especialidades ingresadas.
     *
     * @return {@link List<EspecialidadEntity>} con la información de las especialidades.
     * @param especialidadesIDs IDs de las especialidades.
     * @throws InvalidEspecialidadException si alguna especialidad no existe.
     */
    List<EspecialidadEntity> verificarEspecialidades(List<Long> especialidadesIDs)throws InvalidEspecialidadException;

    /**
     * Verifica que exista una especialidad.
     *
     * @param especialidadId IDs de las especialidades.
     * @return {@link EspecialidadEntity} con la información de la especialidad.
     * @throws InvalidEspecialidadException si alguna especialidad no existe.
     */
    EspecialidadEntity verificarEspecialidades(Long especialidadId) throws InvalidEspecialidadException;
}
