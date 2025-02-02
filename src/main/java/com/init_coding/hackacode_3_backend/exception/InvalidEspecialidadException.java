package com.init_coding.hackacode_3_backend.exception;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidEspecialidadException extends Exception{

    private static final String mensajeSingular = "La especialidad con ID %s no es una especialidad válida";

    private static final String mensajePlural = "Las especialidades con los IDs %s no son especialidades válidas";

    public InvalidEspecialidadException(String message) {
        super(message);
    }

    public InvalidEspecialidadException(Long id) {
        super(String.format(mensajeSingular, String.valueOf(id)));
    }

    public InvalidEspecialidadException(List<Long> ids) {

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
