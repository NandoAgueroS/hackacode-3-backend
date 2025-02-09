package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.dto.request.ConsultaRequest;
import com.init_coding.hackacode_3_backend.dto.response.ConsultaResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import java.util.List;

public interface IConsultaService {

    /**
     * Obtiene todas las consultas registrados.
     *
     * @return Lista de {@link ConsultaResponse} con la información de las consultas.
     */
    List<ConsultaResponse> findAll();

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
     * Elimina una consulta por su ID.
     *
     * @param consultaCodigo ID de la consulta.
     * @throws ResourceNotFoundException si la consulta no existe.
     */
    void delete(Long consultaCodigo)throws ResourceNotFoundException;

    /**
     * Verifica si el medicoId, pacienteId y servicioMedicoCodigo son válidos.
     *
     * @param consultaRequest Consulta de la cuál se verificarán los argumentos.
     * @throws InvalidArgumentException si alguno de los argumentos no es válido.
     */
    void verificarArgumentos(ConsultaRequest consultaRequest) throws InvalidArgumentException;
}
