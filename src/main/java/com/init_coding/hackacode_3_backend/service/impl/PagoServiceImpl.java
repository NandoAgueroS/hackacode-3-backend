package com.init_coding.hackacode_3_backend.service.impl;

import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.repository.IPagoRepository;
import com.init_coding.hackacode_3_backend.repository.IServicioMedicoRepository;
import com.init_coding.hackacode_3_backend.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class PagoServiceImpl implements IPagoService {

    @Autowired
    private IPagoRepository pagoRepository;

    @Autowired
    private IServicioMedicoRepository servicioMedicoRepository;

    @Override
    public BigDecimal getGananciasPorServicioYMes(Long servicioCodigo, int mes) throws InvalidArgumentException, InvalidServicioException {

        if (mes <= 0 || mes > 12) throw new InvalidArgumentException("El mes tiene que ser un número entre 1 y 12");

        if (!servicioMedicoRepository.existsByCodigoAndActivoTrue(servicioCodigo)) throw new InvalidServicioException(servicioCodigo);

        return pagoRepository.getGananciasByServicioAndMes(servicioCodigo, mes);
    }

    @Override
    public BigDecimal getGananciasPorServicioYFecha(Long servicioCodigo, LocalDate fecha) throws InvalidServicioException {
        if (!servicioMedicoRepository.existsByCodigoAndActivoTrue(servicioCodigo)) throw new InvalidServicioException(servicioCodigo);
        return pagoRepository.getGananciasByServicioAndMes(servicioCodigo, fecha);
    }

    @Override
    public BigDecimal getGananciasPorDia(LocalDate fecha) {
        return pagoRepository.getGananciasByFecha(fecha);
    }

    @Override
    public BigDecimal getGananciasTotales() {
        return pagoRepository.getGananciasTotales();
    }
}
