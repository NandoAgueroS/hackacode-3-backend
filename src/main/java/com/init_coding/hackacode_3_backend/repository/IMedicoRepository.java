package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface IMedicoRepository extends JpaRepository<MedicoEntity, Long> {

    List<MedicoEntity> findAllByEspecialidadesId(Long especialidadId);

}
