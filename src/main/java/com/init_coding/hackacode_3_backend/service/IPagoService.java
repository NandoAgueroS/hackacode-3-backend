package com.init_coding.hackacode_3_backend.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface IPagoService {

    BigDecimal getGananciasPorServicioYMes(Long servicioCodigo, int mes);
    BigDecimal getGananciasPorServicioYFecha(Long servicioCodigo, LocalDate fecha);
    BigDecimal getGananciasPorDia(LocalDate fecha);


}
