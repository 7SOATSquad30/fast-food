package br.com.fiap.grupo30.fastfood.application.services.exceptions;

public class CantAddProductToOrderException extends RuntimeException {
    public CantAddProductToOrderException(String msg) {
        super(msg);
    }

    public CantAddProductToOrderException(String msg, Throwable exception) {
        super(msg, exception);
    }
}
