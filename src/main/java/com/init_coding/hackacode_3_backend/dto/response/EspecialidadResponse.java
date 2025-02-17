package com.init_coding.hackacode_3_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EspecialidadResponse {

    private Long id;

    private String nombre;

    private boolean activo;
}
