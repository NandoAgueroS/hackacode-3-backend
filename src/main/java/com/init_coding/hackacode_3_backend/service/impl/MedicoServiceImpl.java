package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.dto.response.TurnoDisponibleResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.mapper.MedicoMapper;
import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import com.init_coding.hackacode_3_backend.model.DisponibilidadEntity;
import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import com.init_coding.hackacode_3_backend.repository.IConsultaRepository;
import com.init_coding.hackacode_3_backend.repository.IEspecialidadRepository;
import com.init_coding.hackacode_3_backend.repository.IMedicoRepository;
import com.init_coding.hackacode_3_backend.service.IEspecialidadService;
import com.init_coding.hackacode_3_backend.service.IMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Service
public class MedicoServiceImpl implements IMedicoService {

    @Autowired
    IMedicoRepository medicoRepository;

    @Autowired
    IEspecialidadRepository especialidadRepository;

    @Autowired
    IEspecialidadService especialidadService;

    @Autowired
    private IConsultaRepository consultaRepository;

    MedicoMapper medicoMapper = MedicoMapper.mapper;

    @Override
    public List<MedicoResponse> findAll() {
        List<MedicoResponse> medicoResponses = medicoMapper.toResponseList(medicoRepository.findAllByActivoTrue());
        return medicoResponses;
    }

    @Override
    public List<MedicoResponse> findAllInactivos() {
        List<MedicoResponse> medicoResponses = medicoMapper.toResponseList(medicoRepository.findAllByActivoFalse());
        return medicoResponses;
    }

    @Override
    public List<MedicoResponse> findByEspecialidad(Long especialidadId) throws InvalidEspecialidadException {
        especialidadService.verificarEspecialidades(especialidadId);

        return medicoMapper.toResponseList(medicoRepository.findAllByEspecialidad_idAndActivoTrue(especialidadId));
    }

    @Override
    public MedicoResponse create(MedicoRequest medico) throws InvalidEspecialidadException {
        EspecialidadEntity especialidad = especialidadService.verificarEspecialidades(
                medico.getEspecialidadId());

        MedicoEntity medicoEntity = medicoMapper.toEntity(medico);
        //medicoEntity.setEspecialidades(especialidades);
        medicoRepository.save(medicoEntity);

        return medicoMapper.toResponse(medicoRepository.findById(medicoEntity.getId()).orElse(null));
    }

    @Override
    public MedicoResponse update(Long medicoId, MedicoRequest medico)
            throws ResourceNotFoundException, InvalidEspecialidadException {
        MedicoEntity medicoEntity = medicoRepository.findByIdAndActivoTrue(medicoId).orElseThrow(()->
                new ResourceNotFoundException("modificar", "Medico", medicoId));

        EspecialidadEntity especialidad = especialidadService.verificarEspecialidades(
                medico.getEspecialidadId());

        MedicoEntity medicoModificado = medicoMapper.toEntity(medico);

        //medicoModificado.setEspecialidades(especialidadService.verificarEspecialidades(ids));
        medicoModificado.setId(medicoId);

        medicoRepository.save(medicoModificado);

        return medicoMapper.toResponse(medicoRepository.findByIdAndActivoTrue(medicoModificado.getId()).orElse(null));
    }

    @Override
    public MedicoResponse findById(Long medicoId) throws ResourceNotFoundException {
        return medicoMapper.toResponse(medicoRepository.findByIdAndActivoTrue(medicoId).orElseThrow(()->
                new ResourceNotFoundException("buscar", "Médico", medicoId)));
    }

    @Transactional
    @Override
    public void updateActivo(Long medicoId, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!medicoRepository.existsByIdAndActivoTrue(medicoId))
                throw new ResourceNotFoundException("eliminar", "Médico", medicoId);
        }else{
            MedicoEntity medico = medicoRepository.findById(medicoId).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "Médico", medicoId));
            if (medico.isActivo()) throw new EntityAlreadyActivaException("Médico", medicoId);
        }
        medicoRepository.updateActivoById(medicoId, esActivo);
    }

    @Override
    public boolean isValid(Long medicoId){
        return medicoRepository.existsByIdAndActivoTrue(medicoId);
    }

    @Override
    public List<TurnoDisponibleResponse> getTurnosDisponiblesByMedicoIdYMes(Long medicoId, int mes) throws InvalidArgumentException, ResourceNotFoundException{
        if (mes < 1 || mes >12) throw new InvalidArgumentException("El mes tiene que ser un número entre 1 y 12");

        MedicoEntity medico = medicoRepository.findByIdAndActivoTrue(medicoId).orElseThrow(() ->
                new ResourceNotFoundException("obtener turnos", "medico", medicoId));

        List<DisponibilidadEntity> disponibilidades = medico.getDisponibilidades();

        List<ConsultaEntity> consultas = consultaRepository.findAllByMedico_idAndMesAndActivoTrue(medicoId, mes);

        List<LocalDateTime> consultasOcupadas = consultas.stream().map(consulta -> LocalDateTime.of(consulta.getFecha(), consulta.getHora())).toList();

        List<TurnoDisponibleResponse> turnosDisponibles = new LinkedList<>();

        for (DisponibilidadEntity disponibilidad : disponibilidades){

            LocalDate fecha = LocalDate.of(2025, mes, 1);
            LocalTime hora = disponibilidad.getHoraInicio();

            while (fecha.isBefore(LocalDate.of(2025, mes, 1).plusMonths(1L).minusDays(1L))){

                if (fecha.getDayOfWeek().equals(disponibilidad.getDiaSemana())){

                    while (hora.isBefore(disponibilidad.getHoraFin())){

                        if (!consultasOcupadas.contains(LocalDateTime.of(fecha, hora))){
                        turnosDisponibles.add(
                                TurnoDisponibleResponse.builder()
                                        .fecha(fecha)
                                        .duracion(30)
                                        .medicoId(medicoId)
                                        .hora(hora)
                                .build());
                        }
                        hora = hora.plusMinutes(30L);

                    }

                }
                fecha = fecha.plusDays(1);
                hora = disponibilidad.getHoraInicio();

            }

        }

        Collections.sort(
                turnosDisponibles,
                Comparator
                        .comparing(TurnoDisponibleResponse::getFecha)
                        .thenComparing(TurnoDisponibleResponse::getHora));

        return turnosDisponibles;
    }
}
