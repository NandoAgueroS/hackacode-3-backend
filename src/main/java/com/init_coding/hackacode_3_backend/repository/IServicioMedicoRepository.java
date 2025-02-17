package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.ServicioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IServicioMedicoRepository extends JpaRepository<ServicioMedico, Long> {
    boolean existsByCodigoAndActivoTrue(Long codigo);
}
