package com.init_coding.hackacode_3_backend.dto.request;

import com.init_coding.hackacode_3_backend.model.ServicioIndividualEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaqueteServiciosRequest extends ServicioMedicoRequest{

    List<Long> servicios;

}
