package com.init_coding.hackacode_3_backend.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorMessageResponse {

    private HttpStatus status;

    private String error;

    private String message;
}
