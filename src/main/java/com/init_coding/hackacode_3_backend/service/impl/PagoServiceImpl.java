package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.repository.IPagoRepository;
import com.init_coding.hackacode_3_backend.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Override
    public BigDecimal getGananciasPorServicioYMes(Long servicioCodigo, int mes) {
        BigDecimal ganancias = pagoRepository.getGananciasByServicioAndMes(servicioCodigo, mes);
        return ganancias;
    }

    @Override
    public BigDecimal getGananciasPorServicioYFecha(Long servicioCodigo, LocalDate fecha) {
        return pagoRepository.getGananciasByServicioAndMes(servicioCodigo, fecha);
    }

    @Override
    public BigDecimal getGananciasPorDia(LocalDate fecha) {
        return pagoRepository.getGananciasByFecha(fecha);
    }
}
