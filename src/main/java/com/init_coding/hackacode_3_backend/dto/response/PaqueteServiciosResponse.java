package com.init_coding.hackacode_3_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteServiciosResponse extends ServicioMedicoResponse{

    List<ServicioIndividualResponse> servicios;

}
