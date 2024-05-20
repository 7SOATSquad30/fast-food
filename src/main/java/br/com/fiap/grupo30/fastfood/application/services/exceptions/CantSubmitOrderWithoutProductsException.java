package br.com.fiap.grupo30.fastfood.application.services.exceptions;

import java.io.Serial;

public class CantSubmitOrderWithoutProductsException extends DomainValidationException {
    @Serial private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Can not submit order without products";

    public CantSubmitOrderWithoutProductsException() {
        super(MESSAGE);
    }

    public CantSubmitOrderWithoutProductsException(Throwable exception) {
        super(MESSAGE, exception);
    }
}
