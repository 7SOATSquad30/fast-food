package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.MercadoPagoAdapter;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoPaymentDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoPaymentStatus;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.events.MercadoPagoActionEventDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectOrderPaymentViaMercadoPagoUseCase {
    private final JpaOrderRepository orderRepository;
    private final MercadoPagoAdapter mercadoPagoAdapter;

    @Autowired
    public CollectOrderPaymentViaMercadoPagoUseCase(
            JpaOrderRepository orderRepository, MercadoPagoAdapter mercadoPagoAdapter) {
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
