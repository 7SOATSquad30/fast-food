package br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago;

public enum MercadoPagoPaymentStatus {
    APPROVED("approved");

    private String value;

    MercadoPagoPaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
