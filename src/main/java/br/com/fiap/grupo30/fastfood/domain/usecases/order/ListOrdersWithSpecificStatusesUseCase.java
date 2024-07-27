package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import java.util.List;

public class ListOrdersWithSpecificStatusesUseCase {

    private final OrderGateway orderGateway;

    public ListOrdersWithSpecificStatusesUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<OrderDTO> execute() {
        return orderGateway.findOrdersWithSpecificStatuses().stream().map(Order::toDTO).toList();
    }
}
