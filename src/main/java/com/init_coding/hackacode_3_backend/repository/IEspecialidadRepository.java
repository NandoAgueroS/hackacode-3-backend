package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.EspecialidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEspecialidadRepository extends JpaRepository<EspecialidadEntity, Long> {
    List<EspecialidadEntity> findAllByActivoTrue();

    List<EspecialidadEntity> findAllByActivoFalse();

    Optional<EspecialidadEntity> findByIdAndActivoTrue(Long id);

    List<EspecialidadEntity> findAllByIdInAndActivoTrue(List<Long> ids);

    boolean existsByIdAndActivoTrue(Long id);

    @Modifying
    @Query("UPDATE EspecialidadEntity e SET e.activo = :activo WHERE e.id = :id")
    void updateActivoById(@Param("id") Long id, @Param("activo") boolean activo);

}
