package com.init_coding.hackacode_3_backend.exception;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidArgumentException extends Exception{

    private static final String MENSAJE_SINGULAR_FEMENINO = "La %s con ID %s no es una %s válida";

    private static final String MENSAJE_PLURAL_FEMENINO = "Las %s con los IDs %s no son %s válidas";

    private static final String MENSAJE_PLURAL_MASCULINO = "Los %s con los IDs %s no son %s válidos";

    private static final String MENSAJE_SINGULAR_MASCULINO = "El %s con ID %s no es un %s válido";



    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String argumentName, Long argumentId, boolean isMasculine) {
        super(String.format(
                isMasculine ? MENSAJE_SINGULAR_MASCULINO : MENSAJE_SINGULAR_FEMENINO,
                argumentName,
                argumentId,
                argumentName));
    }

    public InvalidArgumentException(String argumentName, List<Long> argumentIds, boolean isMasculine) {
        super(String.format(
                isMasculine ? MENSAJE_PLURAL_MASCULINO : MENSAJE_PLURAL_FEMENINO,
                argumentName,
                argumentIds.stream().map(String::valueOf).collect(Collectors.joining(", ")),
                argumentName));
    }



}
