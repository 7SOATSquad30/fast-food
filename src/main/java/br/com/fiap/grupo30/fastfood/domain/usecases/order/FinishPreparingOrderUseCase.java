package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderStatusReadyOtherThanPreparingException;

public class FinishPreparingOrderUseCase {

    public OrderDTO execute(OrderGateway orderGateway, Long orderId) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.PREPARING) {
            throw new CantChangeOrderStatusReadyOtherThanPreparingException();
        }

        order.setStatus(OrderStatus.READY);
        return orderGateway.save(order).toDTO();
    }
}
