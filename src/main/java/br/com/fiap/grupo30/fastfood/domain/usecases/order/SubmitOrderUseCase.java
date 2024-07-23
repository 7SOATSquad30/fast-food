package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;

public class SubmitOrderUseCase {

    private final OrderGateway orderGateway;

    public SubmitOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order execute(Long orderId) {
        Order order = orderGateway.findById(orderId);
        order.setStatus(OrderStatus.SUBMITTED);
        return orderGateway.submitOrder(order);
    }
}
