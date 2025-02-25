package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.ConsultaEntity;
import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IConsultaRepository extends JpaRepository<ConsultaEntity, Long> {

    List<ConsultaEntity> findAllByMedico_idAndActivoTrue(Long medicoId);

    @Query("SELECT c FROM ConsultaEntity c WHERE c.medico.id = :medicoId AND MONTH(c.fecha) = :mes AND YEAR(c.fecha) = :anio AND activo = true")
    List<ConsultaEntity> findAllByMedico_idAndMesAndAnioAndActivoTrue(Long medicoId, int mes, int anio);

    @Query("SELECT c FROM ConsultaEntity c WHERE MONTH(c.fecha) = :mes AND YEAR(c.fecha) = :anio AND activo = true")
    List<ConsultaEntity> findAllByMesAndAnioAndActivoTrue(int mes, int anio);

    List<ConsultaEntity> findAllByPaciente_idAndActivoTrue(Long pacienteId);

    List<ConsultaEntity> findAllByActivoTrue();

    List<ConsultaEntity> findAllByActivoFalse();

    Optional<ConsultaEntity> findByCodigoAndActivoTrue(Long codigo);

    boolean existsByCodigoAndActivoTrue(Long codigo);

    @Modifying
    @Query("UPDATE ConsultaEntity c SET c.activo = :activo WHERE c.codigo = :codigo")
    void updateActivoById(@Param("codigo") Long codigo, @Param("activo") boolean activo);
}
