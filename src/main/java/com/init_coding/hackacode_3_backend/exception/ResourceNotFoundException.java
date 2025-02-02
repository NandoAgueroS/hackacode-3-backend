package com.init_coding.hackacode_3_backend.exception;

public class ResourceNotFoundException extends Exception{

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String accion, String entity, Long entityId) {
        super(String.format("Fallo al %s: No se encontró la entidad %s con ID %d", accion, entity, entityId));
    }

}
