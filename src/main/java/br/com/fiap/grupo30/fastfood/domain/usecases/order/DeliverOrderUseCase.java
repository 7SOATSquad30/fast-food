package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderStatusDeliveredOtherThanReadyException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeliverOrderUseCase {

    private final OrderRepository orderRepository;

    @Autowired
    public DeliverOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO execute(Long orderId) {
        OrderEntity order =
                this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.READY) {
            throw new CantChangeOrderStatusDeliveredOtherThanReadyException();
        }

        order.setStatus(OrderStatus.DELIVERED);
        return this.orderRepository.save(order).toDTO();
    }
}
