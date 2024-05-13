package br.com.fiap.grupo30.fastfood.application.services.exceptions;

public class UserCantChangeOrderAfterSubmitException extends RuntimeException {
    public UserCantChangeOrderAfterSubmitException(String msg) {
        super(msg);
    }

    public UserCantChangeOrderAfterSubmitException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
