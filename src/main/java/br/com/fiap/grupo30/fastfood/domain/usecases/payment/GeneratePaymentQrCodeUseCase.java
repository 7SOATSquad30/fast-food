package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.PaymentProcessingFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratePaymentQrCodeUseCase {

    @Autowired
    public GeneratePaymentQrCodeUseCase() {
    }

    public PaymentQrCodeDTO execute(OrderGateway orderGateway, MercadoPagoGateway mercadoPagoGateway, Long orderId) {
        Order order = orderGateway.findById(orderId);

        MercadoPagoQrCodeDTO qrCodeResponse;
        try {
            qrCodeResponse = mercadoPagoGateway.createQrCodeForOrderPaymentCollection(order.toDTO());
        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not start payment processing", e);
        }

        order.setPaymentProcessing();
        orderGateway.save(order);

        return new PaymentQrCodeDTO(qrCodeResponse.getQrData());
    }
}
