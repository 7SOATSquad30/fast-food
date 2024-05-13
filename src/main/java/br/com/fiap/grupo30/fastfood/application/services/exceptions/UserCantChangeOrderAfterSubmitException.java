package br.com.fiap.grupo30.fastfood.application.services.exceptions;

import java.io.Serial;

public class UserCantChangeOrderAfterSubmitException extends RuntimeException {

    @Serial private static final long serialVersionUID = 1L;

    public UserCantChangeOrderAfterSubmitException(String msg) {
        super(msg);
    }

    public UserCantChangeOrderAfterSubmitException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
