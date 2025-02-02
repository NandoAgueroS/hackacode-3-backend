package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPacienteRepository extends JpaRepository<PacienteEntity, Long> {
}
