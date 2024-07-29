package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;

public class GetOrderUseCase {

    private final OrderGateway orderGateway;

    public GetOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public OrderDTO execute(Long orderId) {
        return orderGateway.findById(orderId).toDTO();
    }
}
