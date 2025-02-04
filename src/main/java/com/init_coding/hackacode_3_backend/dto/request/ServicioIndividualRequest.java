package com.init_coding.hackacode_3_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServicioIndividualRequest extends ServicioMedicoRequest{

    private BigDecimal precio;

    private String descripcion;

}
