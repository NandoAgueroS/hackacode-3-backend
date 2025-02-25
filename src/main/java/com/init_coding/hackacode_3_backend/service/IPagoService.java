package com.init_coding.hackacode_3_backend.service;

import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IPagoService {

    BigDecimal getGananciasPorServicioYMes(Long servicioCodigo, int mes) throws InvalidArgumentException, InvalidServicioException;

    BigDecimal getGananciasPorServicioYFecha(Long servicioCodigo, LocalDate fecha) throws InvalidServicioException;

    BigDecimal getGananciasPorDia(LocalDate fecha);

    BigDecimal getGananciasTotales();

}
