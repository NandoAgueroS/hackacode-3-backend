package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMedicoRepository extends JpaRepository<MedicoEntity, Long> {

    List<MedicoEntity> findAllByEspecialidad_idAndActivoTrue(Long especialidadId);

    List<MedicoEntity> findAllByActivoTrue();

    List<MedicoEntity> findAllByActivoFalse();

    Optional<MedicoEntity> findByIdAndActivoTrue(Long id);

    boolean existsByIdAndActivoTrue(Long id);

    @Modifying
    @Query("UPDATE MedicoEntity m SET m.activo = :activo WHERE m.id = :id")
    void updateActivoById(@Param("id") Long id, @Param("activo") boolean activo);
}
