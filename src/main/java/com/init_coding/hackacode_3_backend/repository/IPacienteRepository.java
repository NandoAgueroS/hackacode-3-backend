package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPacienteRepository extends JpaRepository<PacienteEntity, Long> {

    List<PacienteEntity> findAllByActivoTrue();

    List<PacienteEntity> findAllByActivoFalse();

    Optional<PacienteEntity> findByIdAndActivoTrue(Long id);

    Optional<PacienteEntity> findByDniAndActivoTrue(String dni);

    boolean existsByIdAndActivoTrue(Long id);

    @Modifying
    @Query("UPDATE PacienteEntity p SET p.activo = :activo WHERE p.id = :id")
    void updateActivoById(@Param("id") Long id, @Param("activo") boolean activo);

}
