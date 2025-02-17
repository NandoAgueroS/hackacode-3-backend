package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.PaqueteServiciosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IPaqueteServiciosRepository extends JpaRepository<PaqueteServiciosEntity, Long> {

    List<PaqueteServiciosEntity> findAllByActivoTrue();

    List<PaqueteServiciosEntity> findAllByActivoFalse();

    Optional<PaqueteServiciosEntity> findByCodigoAndActivoTrue(Long codigo);

    List<PaqueteServiciosEntity> findAllByCodigoInAndActivoTrue(List<Long> codigos);

    boolean existsByCodigoAndActivoTrue(Long codigo);

    @Modifying
    @Query("UPDATE PaqueteServiciosEntity p SET p.activo = :activo WHERE p.id = :id")
    void updateActivoById(@Param("id") Long id, @Param("activo") boolean activo);

}
