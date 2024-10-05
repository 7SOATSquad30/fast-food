package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.InvalidOrderStatusException;
import java.util.List;
import java.util.Locale;

public class ListOrdersByStatusUseCase {

    public List<OrderDTO> execute(OrderGateway orderGateway, String status) {
        OrderStatus statusFilter = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusFilter = OrderStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
            } catch (IllegalArgumentException e) {
                throw new InvalidOrderStatusException(status, e);
            }
        }
        return orderGateway.findOrdersByStatus(statusFilter).stream().map(Order::toDTO).toList();
    }
}
