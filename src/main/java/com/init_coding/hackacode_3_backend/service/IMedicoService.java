package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.dto.response.TurnoDisponibleResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;

import java.time.DayOfWeek;
import java.util.List;

public interface IMedicoService {
    /**
     * Obtiene todos los médicos activos.
     *
     * @return Lista de {@link MedicoResponse} con la información de los médicos.
     */
    List<MedicoResponse> findAll();

    /**
     * Obtiene todos los médicos inactivos.
     *
     * @return Lista de {@link MedicoResponse} con la información de los médicos.
     */
    List<MedicoResponse> findAllInactivos();

    /**
     * Obtiene todos los médicos registrados con la especialidad indicada.
     *
     * @return Lista de {@link MedicoResponse} con la información de los médicos.
     * @param especialidadId id de la especialidad.
     * @throws com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException si la especialidad no existe.
     */
    List<MedicoResponse> findByEspecialidad(Long especialidadId) throws InvalidEspecialidadException;

    /**
     * Crea un medico.
     *
     * @return {@link MedicoResponse} con la información del médico creado.
     * @param medico Datos del médico a guardar.
     * @throws com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException si la especialidad no existe.
     */
    MedicoResponse create(MedicoRequest medico) throws InvalidEspecialidadException;

    /**
     * Actualiza los datos de un médico existente.
     *
     * @return {@link MedicoResponse} con la información del médico.
     * @throws ResourceNotFoundException si el médico no existe.
     * @throws com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException si la especialidad no existe.
     */
    MedicoResponse update(Long medicoId, MedicoRequest medico)throws ResourceNotFoundException, InvalidEspecialidadException;

    /**
     * Busca un médico por su ID.
     *
     * @return {@link MedicoResponse} con la información del médico.
     * @param medicoId ID del médico.
     * @throws ResourceNotFoundException si el médico no existe.
     */
    MedicoResponse findById(Long medicoId)throws ResourceNotFoundException;

    /**
     * Permite activar o desactivar un médico por su ID.
     *
     * @param medicoId ID del médico.
     * @param esActivo Valor actualizado para la propiedad activo.
     * @throws ResourceNotFoundException si el medico no existe.
     * @throws EntityAlreadyActivaException si el medico ya se encuentra activo.
     */
    void updateActivo(Long medicoId, boolean esActivo)throws ResourceNotFoundException, EntityAlreadyActivaException;

    /**
     * Verifica si el medico es válido.
     *
     * @param medicoId ID del médico.
     * @return boolean indicando si el médico existe o no.
     */
    boolean isValid(Long medicoId);

    /**
     * Obtiene los turnos disponibles para un mes y año de un médico por su ID.
     * @param medicoId ID del médico
     * @param mes Mes para el cuál se buscarán turnos disponibles
     * @param anio Año para el cuál se buscarán turnos disponibles
     * @return Lista de {@link TurnoDisponibleResponse} con la información de los turnos disponibles
     * @throws InvalidArgumentException si el mes ingresado no es válido.
     * @throws ResourceNotFoundException si el médico no existe.
     */
    List<TurnoDisponibleResponse> getTurnosDisponiblesByMedicoIdYMesYAnio(Long medicoId, int mes, int anio) throws InvalidArgumentException, ResourceNotFoundException;

    /**
     * Obtiene los turnos disponibles para un mes, año y día de la semana de un médico por su ID.
     * @param medicoId ID del médico
     * @param mes Mes para el cuál se buscarán turnos disponibles
     * @param anio Año para el cuál se buscarán turnos disponibles
     * @param diaDeLaSemana Dia de la semana para el cuál se buscarán turnos disponibles
     * @return Lista de {@link TurnoDisponibleResponse} con la información de los turnos disponibles
     * @throws InvalidArgumentException si el mes ingresado no es válido.
     * @throws ResourceNotFoundException si el médico no existe.
     */
    List<TurnoDisponibleResponse> getTurnosDisponiblesByMedicoIdYMesYAnioYDiaDeLaSemana(Long medicoId, int mes, int anio, DayOfWeek diaDeLaSemana) throws InvalidArgumentException, ResourceNotFoundException;

}
