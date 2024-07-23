package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderStatusPreparingOtherThanSubmittedException;

public class StartPreparingOrderUseCase {

    private final OrderGateway orderGateway;

    public StartPreparingOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public Order execute(Long orderId) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.SUBMITTED) {
            throw new CantChangeOrderStatusPreparingOtherThanSubmittedException();
        }

        order.setStatus(OrderStatus.PREPARING);
        return orderGateway.startPreparingOrder(order);
    }
}
