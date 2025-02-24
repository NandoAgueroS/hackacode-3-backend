package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.dto.request.ConsultaRequest;
import com.init_coding.hackacode_3_backend.dto.response.ConsultaResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.mapper.ConsultaMapper;
import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import com.init_coding.hackacode_3_backend.model.PacienteEntity;
import com.init_coding.hackacode_3_backend.model.PagoEntity;
import com.init_coding.hackacode_3_backend.model.ServicioMedico;
import com.init_coding.hackacode_3_backend.repository.IConsultaRepository;
import com.init_coding.hackacode_3_backend.repository.IPacienteRepository;
import com.init_coding.hackacode_3_backend.repository.IServicioMedicoRepository;
import com.init_coding.hackacode_3_backend.service.IConsultaService;
import com.init_coding.hackacode_3_backend.service.IMedicoService;
import com.init_coding.hackacode_3_backend.service.IPacienteService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConsultaServiceImpl implements IConsultaService {

    @Autowired
    private IConsultaRepository consultaRepository;

    private ConsultaMapper consultaMapper = ConsultaMapper.mapper;

    @Autowired
    private IMedicoService medicoService;

    @Autowired
    private IPacienteService pacienteService;

    @Autowired
    private IPacienteRepository pacienteRepository;

    @Autowired
    private IServicioMedicoRepository servicioMedicoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ConsultaResponse> findAll() {
        return consultaMapper.toResponseList(
                consultaRepository.findAllByActivoTrue()
        );
    }

    @Override
    public List<ConsultaResponse> findAllInactivas() {
        return consultaMapper.toResponseList(
                consultaRepository.findAllByActivoFalse()
        );
    }

    @Override
    public ConsultaResponse create(ConsultaRequest consulta) throws InvalidArgumentException {
        verificarArgumentos(consulta);
        ServicioMedico servicioMedico = servicioMedicoRepository.findById(consulta.getServicioMedicoCodigo()).orElse(null);

        PacienteEntity pacienteEntity = pacienteRepository.findByIdAndActivoTrue(consulta.getPacienteId()).orElse(null);

        PagoEntity pagoEntity = new PagoEntity();
        BigDecimal total = servicioMedico.getPrecio();
        if (pacienteEntity.isTieneObraSocial()){

            BigDecimal descuento = new BigDecimal("0.20");

            pagoEntity.setTotal(total.subtract(total.multiply(descuento)));
        }else{
            pagoEntity.setTotal(total);
        }

        pagoEntity.setEsPagado(consulta.isEsPagado());
        pagoEntity.setMetodo(consulta.getMetodoPago());

        ConsultaEntity consultaEntity = consultaMapper.toEntity(consulta);
        consultaEntity.setServicioMedico(servicioMedico);
        consultaEntity.setPago(pagoEntity);

        consultaRepository.save(consultaEntity);

        return consultaMapper.toResponse(
                consultaEntity
        );
    }

    @Override
    public ConsultaResponse update(Long consultaCodigo, ConsultaRequest consulta) throws ResourceNotFoundException, InvalidArgumentException {
        if (!consultaRepository.existsByCodigoAndActivoTrue(consultaCodigo))
            throw new ResourceNotFoundException("modificar", "Consulta", consultaCodigo);

        verificarArgumentos(consulta);

        ConsultaEntity consultaModificadaEntity = consultaMapper.toEntity(consulta);
        consultaModificadaEntity.setCodigo(consultaCodigo);

        ServicioMedico servicioMedico = servicioMedicoRepository.findById(consulta.getServicioMedicoCodigo()).orElse(null);

        PacienteEntity pacienteEntity = pacienteRepository.findByIdAndActivoTrue(consulta.getPacienteId()).orElse(null);

        PagoEntity pagoEntity = new PagoEntity();
        BigDecimal total = servicioMedico.getPrecio();
        if (pacienteEntity.isTieneObraSocial()){

            BigDecimal descuento = new BigDecimal("0.20");

            pagoEntity.setTotal(total.subtract(total.multiply(descuento)));
        }else{
            pagoEntity.setTotal(total);
        }

        pagoEntity.setEsPagado(consulta.isEsPagado());
        pagoEntity.setMetodo(consulta.getMetodoPago());
        consultaModificadaEntity.setServicioMedico(servicioMedico);
        consultaModificadaEntity.setPago(pagoEntity);

        consultaRepository.save(consultaModificadaEntity);

        return consultaMapper.toResponse(
                consultaModificadaEntity
        );
    }

    @Override
    public ConsultaResponse findById(Long consultaCodigo) throws ResourceNotFoundException {return consultaMapper.toResponse(
                consultaRepository.findByCodigoAndActivoTrue(consultaCodigo).orElseThrow(() ->
                        new ResourceNotFoundException("buscar", "Consulta", consultaCodigo)
                )
        );
    }

    @Transactional
    @Override
    public void updateActivo(Long consultaCodigo, boolean esActivo) throws ResourceNotFoundException, EntityAlreadyActivaException {
        if (!esActivo) {
            if (!consultaRepository.existsByCodigoAndActivoTrue(consultaCodigo))
                    throw new ResourceNotFoundException("eliminar", "Consulta", consultaCodigo);
        }else{
            ConsultaEntity consulta = consultaRepository.findById(consultaCodigo).orElseThrow(() ->
                    new ResourceNotFoundException("reactivar", "Consulta", consultaCodigo));
            if (consulta.isActivo()) throw new EntityAlreadyActivaException("Consulta", consultaCodigo);
        }

        consultaRepository.updateActivoById(consultaCodigo, esActivo);
    }

    @Override
    public List<ConsultaResponse> findAllByPaciente(Long pacienteId) throws InvalidArgumentException {
        if (!pacienteService.isValid(pacienteId)) throw new InvalidArgumentException("paciente", pacienteId, true);

        return consultaMapper.toResponseList(
                consultaRepository.findAllByPaciente_idAndActivoTrue(pacienteId)
        );
    }

    @Override
    public List<ConsultaResponse> findAllByMedico(Long medicoId) throws InvalidArgumentException {
        if (!medicoService.isValid(medicoId)) throw new InvalidArgumentException("médico", medicoId, true);

        return consultaMapper.toResponseList(
                consultaRepository.findAllByMedico_idAndActivoTrue(medicoId)
        );
    }

    @Override
    public void verificarArgumentos(ConsultaRequest consultaRequest) throws InvalidArgumentException{
        Long medicoId = consultaRequest.getMedicoId();

        Long pacienteId = consultaRequest.getPacienteId();

        Long servicioMedicoCodigo = consultaRequest.getServicioMedicoCodigo() ;

        if (!medicoService.isValid(consultaRequest.getMedicoId())) throw new InvalidArgumentException("medico", medicoId, true);

        if (!pacienteService.isValid(pacienteId)) throw new InvalidArgumentException("paciente", pacienteId, true);

        if (!servicioMedicoRepository.existsByCodigoAndActivoTrue(servicioMedicoCodigo)) throw new InvalidArgumentException("servicio", servicioMedicoCodigo, true);
    }
}
