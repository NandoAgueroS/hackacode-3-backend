package com.init_coding.hackacode_3_backend.dto.response;

import java.util.List;

public record ConsultasMesResponse(
        int cantidad,
        List<ConsultaResponse> consultas
) {
}
