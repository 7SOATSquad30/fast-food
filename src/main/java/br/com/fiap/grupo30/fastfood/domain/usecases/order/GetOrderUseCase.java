package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;

public class GetOrderUseCase {

    public OrderDTO execute(OrderGateway orderGateway, Long orderId) {
        return orderGateway.findById(orderId).toDTO();
    }
}
