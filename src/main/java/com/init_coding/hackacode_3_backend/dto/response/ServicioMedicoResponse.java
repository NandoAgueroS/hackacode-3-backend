package com.init_coding.hackacode_3_backend.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Data
public class ServicioMedicoResponse {

    private Long codigo;

    private BigDecimal precio;

    private String nombre;

    private String tipoServicio;

    private boolean activo;

}
