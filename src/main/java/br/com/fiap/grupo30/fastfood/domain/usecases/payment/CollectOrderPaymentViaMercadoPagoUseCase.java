package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.MercadoPagoAdapter;
import br.com.fiap.grupo30.fastfood.application.dto.mercadopago.MercadoPagoPaymentDTO;
import br.com.fiap.grupo30.fastfood.application.dto.mercadopago.MercadoPagoPaymentStatus;
import br.com.fiap.grupo30.fastfood.application.dto.mercadopago.events.MercadoPagoActionEventDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectOrderPaymentViaMercadoPagoUseCase {
    private final OrderRepository orderRepository;
    private final MercadoPagoAdapter mercadoPagoAdapter;

    @Autowired
    public CollectOrderPaymentViaMercadoPagoUseCase(
            OrderRepository orderRepository, MercadoPagoAdapter mercadoPagoAdapter) {
        this.orderRepository = orderRepository;
        this.mercadoPagoAdapter = mercadoPagoAdapter;
    }

    public void execute(MercadoPagoActionEventDTO mercadoPagoPaymentEvent) {
        try {
            MercadoPagoPaymentDTO payment =
                    this.mercadoPagoAdapter.getPayment(mercadoPagoPaymentEvent.getData().getId());

            OrderEntity order =
                    this.orderRepository
                            .findById(Long.parseLong(payment.getExternalReference()))
                            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

            if (MercadoPagoPaymentStatus.APPROVED.getValue().equals(payment.getStatus())) {
                order.setPaymentCollected(payment.getTransactionAmount());
            } else {
                order.setPaymentRejected();
            }

            this.orderRepository.save(order);

        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not process payment collection", e);
        }
    }
}
