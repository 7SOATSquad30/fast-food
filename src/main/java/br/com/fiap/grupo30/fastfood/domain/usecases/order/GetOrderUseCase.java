package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetOrderUseCase {

    private final OrderRepository orderRepository;

    @Autowired
    public GetOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO execute(Long orderId) {
        OrderEntity order =
                this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return order.toDTO();
    }
}
