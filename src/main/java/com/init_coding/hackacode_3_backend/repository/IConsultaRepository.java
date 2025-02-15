package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IConsultaRepository extends JpaRepository<ConsultaEntity, Long> {

    List<ConsultaEntity> findAllByMedico_id(Long medicoId);

    List<ConsultaEntity> findAllByPaciente_id(Long pacienteId);

}
