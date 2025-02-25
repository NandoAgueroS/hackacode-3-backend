package com.init_coding.hackacode_3_backend.repository;

import com.init_coding.hackacode_3_backend.model.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface IPagoRepository extends JpaRepository<PagoEntity, Long> {

    @Query("SELECT COALESCE(SUM(p.total), 0) FROM PagoEntity p WHERE p.consulta.servicioMedico.codigo = :servicioCodigo AND MONTH(p.consulta.fecha) = :mes")
    BigDecimal getGananciasByServicioAndMes(@Param("servicioCodigo") Long servicioId, @Param("mes") int mes);

    @Query("SELECT COALESCE(SUM(p.total),0) FROM PagoEntity p WHERE p.consulta.servicioMedico.codigo = :servicioCodigo AND p.consulta.fecha = :fecha")
    BigDecimal getGananciasByServicioAndMes(@Param("servicioCodigo") Long servicioId, @Param("fecha")LocalDate fecha);

    @Query("SELECT COALESCE(SUM(p.total),0) FROM PagoEntity p WHERE p.consulta.fecha = :fecha")
    BigDecimal getGananciasByFecha(@Param("fecha")LocalDate fecha);

    @Query("SELECT COALESCE(SUM(p.total),0) FROM PagoEntity p")
    BigDecimal getGananciasTotales();

}
