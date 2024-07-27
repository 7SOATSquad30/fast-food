package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CollectPaymentViaCashRequest;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CollectOrderPaymentViaCashUseCase {
    private final OrderGateway orderGateway;

    @Autowired
    public CollectOrderPaymentViaCashUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public OrderDTO execute(Long orderId, Double paidAmount) {
        Order order = this.orderGateway.findById(orderId);

        order.setPaymentCollected(paidAmount);

        return orderGateway.save(order).toDTO();
    }
}
