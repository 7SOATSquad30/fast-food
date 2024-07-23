package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.InvalidOrderStatusException;
import java.util.List;
import java.util.Locale;

public class ListOrdersUseCase {

    private final OrderGateway orderGateway;

    public ListOrdersUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<Order> execute(String status) {
        OrderStatus orderStatus = null;
        if (status != null && !status.isEmpty()) {
            try {
                orderStatus = OrderStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
            } catch (IllegalArgumentException e) {
                throw new InvalidOrderStatusException(status, e);
            }
        }
        return orderGateway.findOrdersByStatus(orderStatus);
    }
}
