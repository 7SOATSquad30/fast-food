package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderStatusDeliveredOtherThanReadyException;

public class DeliverOrderUseCase {

    private final OrderGateway orderGateway;

    public DeliverOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order execute(Long orderId) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.READY) {
            throw new CantChangeOrderStatusDeliveredOtherThanReadyException();
        }

        order.setStatus(OrderStatus.DELIVERED);
        return orderGateway.deliverOrder(order);
    }
}
