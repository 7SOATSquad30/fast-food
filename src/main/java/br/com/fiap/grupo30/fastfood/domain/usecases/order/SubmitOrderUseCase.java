package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;

public class SubmitOrderUseCase {

    public SubmitOrderUseCase() {
    }

    public OrderDTO execute(Long orderId) {
        Order order = orderGateway.findById(orderId);
        order.setStatus(OrderStatus.SUBMITTED);
        return orderGateway.save(order).toDTO();
    }
}
