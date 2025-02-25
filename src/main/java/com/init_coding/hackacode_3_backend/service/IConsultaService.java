package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.ConsultaRequest;
import com.init_coding.hackacode_3_backend.dto.response.ConsultaResponse;
import com.init_coding.hackacode_3_backend.dto.response.ConsultasMesResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import java.util.List;

public interface IConsultaService {

    /**
     * Obtiene todas las consultas registradas.
     *
     * @return Lista de {@link ConsultaResponse} con la información de las consultas.
     */
    List<ConsultaResponse> findAll();

    /**
     * Obtiene todas las consultas registradas en un determinado mes y año.
     *
     * @return {@link ConsultasMesResponse} con la información de las consultas.
     * @param mes Número del mes por el que se va a buscar.
     * @param anio Número del año por el que se va a buscar.
     * @throws InvalidArgumentException si el mes no es válido.
     */
     ConsultasMesResponse findAllByMesAndAnio(int mes, int anio) throws InvalidArgumentException;

    /**
     * Obtiene todas las consultas activas.
     *
     * @return Lista de {@link ConsultaResponse} con la información de las consultas.
     */
    List<ConsultaResponse> findAllInactivas();

    /**
     * Obtiene todas las consultas registradas de un paciente.
     *
     * @return Lista de {@link ConsultaResponse} con la información de las consultas.
     * @throws InvalidArgumentException si el paciente indicado no existe.
     */
    List<ConsultaResponse> findAllByPaciente(Long pacienteId) throws InvalidArgumentException;

    /**
     * Obtiene todas las consultas registradas de un médico.
     *
     * @return Lista de {@link ConsultaResponse} con la información de las consultas.
     * @throws InvalidArgumentException si el médico indicado no existe.
     */
    List<ConsultaResponse> findAllByMedico(Long medicoId) throws InvalidArgumentException;

    /**
     * Crea una consulta.
     *
     * @return {@link ConsultaResponse} con la información de la consulta creado.
     * @param consulta Datos de la consulta a guardar.
     * @throws InvalidArgumentException si el Medico, Paciente o Servicio Medico de la consulta no existe.
     */
    ConsultaResponse create(ConsultaRequest consulta) throws InvalidArgumentException;

    /**
     * Actualiza los datos de una consulta existente.
     *
     * @param consultaCodigo ID de la consulta
     * @param consulta Datos nuevos de la consulta
     * @return {@link ConsultaResponse} con la información de la consulta actualizado.
     * @throws ResourceNotFoundException si la consulta no existe.
     * @throws InvalidArgumentException si el Medico, Paciente o Servicio Medico de la consulta no existe.
     */
    ConsultaResponse update(Long consultaCodigo, ConsultaRequest consulta)
            throws ResourceNotFoundException, InvalidArgumentException;

    /**
     * Busca una consulta por su ID.
     *
     * @return {@link ConsultaResponse} con la información de la consulta.
     * @param consultaCodigo ID de la consulta.
     * @throws ResourceNotFoundException si la consulta no existe.
     */
    ConsultaResponse findById(Long consultaCodigo)throws ResourceNotFoundException;

    /**
     * Permite activar o desactivar una consulta por su ID.
     *
     * @param consultaCodigo ID de la consulta.
     * @param esActivo Valor actualizado para la propiedad activo.
     * @throws ResourceNotFoundException si la consulta no existe.
     * @throws EntityAlreadyActivaException si la consulta ya se encuentra activa.
     */
    void updateActivo(Long consultaCodigo, boolean esActivo)throws ResourceNotFoundException, EntityAlreadyActivaException;

    /**
     * Verifica si el medicoId, pacienteId y servicioMedicoCodigo son válidos.
     *
     * @param consultaRequest Consulta de la cuál se verificarán los argumentos.
     * @throws InvalidArgumentException si alguno de los argumentos no es válido.
     */
    void verificarArgumentos(ConsultaRequest consultaRequest) throws InvalidArgumentException;
}
