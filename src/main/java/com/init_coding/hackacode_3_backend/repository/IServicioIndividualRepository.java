package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface IServicioIndividualRepository extends JpaRepository<ServicioIndividualEntity, Long> {

    Optional<BigDecimal> getPrecioByCodigo(Long codigo);

    List<BigDecimal> findAllPrecioByCodigoIn(List<Long> codigos);

    @Query("SELECT COALESCE(SUM(s.precio), 0) FROM ServicioIndividualEntity s WHERE s.codigo IN :codigos")
    BigDecimal sumPreciosByCodigos(@Param("codigos") List<Long> codigos);

}
