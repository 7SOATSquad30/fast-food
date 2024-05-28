package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.CantChangeOrderStatusDeliveredOtherThanReadyException;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
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
