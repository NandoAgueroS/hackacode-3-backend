package com.init_coding.hackacode_3_backend.exception;

public class EntityAlreadyActivaException extends Exception{

    public EntityAlreadyActivaException(String message) {
        super(message);
    }

    public EntityAlreadyActivaException(String entity, Long entityId) {
        super(String.format("Fallo al reactivar: La entidad %s con ID %d ya se encuentra activa", entity, entityId));
    }

}
