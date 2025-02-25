package com.init_coding.hackacode_3_backend.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagoResponse {

    private String metodo;

    private boolean esPagado;

    private BigDecimal total;

}
