package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.MercadoPagoAdapter;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoPaymentDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoPaymentStatus;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.events.MercadoPagoActionEventDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.PaymentProcessingFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectOrderPaymentViaMercadoPagoUseCase {
    private final OrderGateway orderGateway;
    private final MercadoPagoAdapter mercadoPagoAdapter;

    @Autowired
    public CollectOrderPaymentViaMercadoPagoUseCase(
            OrderGateway orderGateway, MercadoPagoAdapter mercadoPagoAdapter) {
        this.orderGateway = orderGateway;
        this.mercadoPagoAdapter = mercadoPagoAdapter;
    }

    public OrderDTO execute(MercadoPagoActionEventDTO mercadoPagoPaymentEvent) {
        try {
            MercadoPagoPaymentDTO payment =
                    this.mercadoPagoAdapter.getPayment(mercadoPagoPaymentEvent.getData().getId());

            Order order =
                    this.orderGateway.findById(Long.parseLong(payment.getExternalReference()));

            if (MercadoPagoPaymentStatus.APPROVED.getValue().equals(payment.getStatus())) {
                order.setPaymentCollected(payment.getTransactionAmount());
            } else {
                order.setPaymentRejected();
            }

            return orderGateway.save(order).toDTO();

        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not process payment collection", e);
        }
    }
}
