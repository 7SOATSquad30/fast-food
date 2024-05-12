package br.com.fiap.grupo30.fastfood.application.services.exceptions;

import java.io.Serial;

public class ResourceConflictException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public ResourceConflictException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
