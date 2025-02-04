package com.init_coding.hackacode_3_backend.exception;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidServicioException extends Exception{

    private static final String mensajeSingular = "El servicio con ID %s no es un servicio válido";

    private static final String mensajePlural = "Los servicios con los IDs %s no son servicios válidos";

    public InvalidServicioException(String message) {
        super(message);
    }

    public InvalidServicioException(Long id) {
        super(String.format(mensajeSingular, String.valueOf(id)));
    }

    public InvalidServicioException(List<Long> ids) {

        super(formatearMensaje(ids));
    }

    private static String formatearMensaje(List<Long> ids){
        String idsString = ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        if (ids.size() == 1){
            return String.format(mensajeSingular, idsString);
        }else{
            return String.format(mensajePlural, idsString);
        }
    }

}
