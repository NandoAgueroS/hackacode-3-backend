package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.service.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private IPagoService pagoService;

    @GetMapping("/ganancias/servicio/{servicioCodigo}")
    public BigDecimal getGanancias(
            @RequestParam(name = "fecha", required = false)LocalDate fecha,
            @PathVariable(name = "servicioCodigo") Long servicioCodigo,
            @RequestParam(name = "mes", required = false) Integer mes,
            @RequestParam(name = "anio", required = false) Integer anio) throws InvalidArgumentException, InvalidServicioException {
        if (fecha != null && anio == null && mes == null) return pagoService.getGananciasPorServicioYFecha(servicioCodigo, fecha);
        if (fecha == null && anio != null && mes != null) return pagoService.getGananciasPorServicioYMes(servicioCodigo, mes);
        return null;
    }

    @GetMapping("/ganancias")
    public BigDecimal getGananciasDeHoy(@RequestParam(name = "fecha", required = false) LocalDate fecha){
        if (fecha != null)
            return pagoService.getGananciasPorDia(fecha);
        else
            return pagoService.getGananciasTotales();
    }

}
