package br.com.fiap.grupo30.fastfood.application.services.exceptions;

import java.io.Serial;

public class ResourceBadRequestException extends RuntimeException {
    @Serial private static final long serialVersionUID = 1L;

    public ResourceBadRequestException(String msg) {
        super(msg);
    }
}
