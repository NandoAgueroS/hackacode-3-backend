package com.init_coding.hackacode_3_backend.exception;

import com.init_coding.hackacode_3_backend.dto.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageResponse> resourceNotFoundExceptionHanlder(ResourceNotFoundException exception){
        ErrorMessageResponse message = ErrorMessageResponse.builder()
                .message(exception.getMessage())
                .error("Recurso no encontrado")
                .status(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(InvalidEspecialidadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageResponse> invalidEspecialidadExceptionHanlder(InvalidEspecialidadException exception){
        ErrorMessageResponse message = ErrorMessageResponse.builder()
                .message(exception.getMessage())
                .error("Especialidad inválida")
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(InvalidServicioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageResponse> invalidServicioExceptionHandler(InvalidServicioException exception){
        ErrorMessageResponse message = ErrorMessageResponse.builder()
                .message(exception.getMessage())
                .error("Servicio inválido")
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessageResponse> invalidArgumentExceptionHanlder(InvalidArgumentException exception){
        ErrorMessageResponse message = ErrorMessageResponse.builder()
                .message(exception.getMessage())
                .error("Argumento invalido")
                .status(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
