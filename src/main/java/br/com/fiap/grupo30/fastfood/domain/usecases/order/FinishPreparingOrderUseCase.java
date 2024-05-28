package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.CantChangeOrderStatusReadyOtherThanPreparingException;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinishPreparingOrderUseCase {

    private final OrderRepository orderRepository;

    @Autowired
    public FinishPreparingOrderUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO execute(Long orderId) {
        OrderEntity order =
                this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.PREPARING) {
            throw new CantChangeOrderStatusReadyOtherThanPreparingException();
        }

        order.setStatus(OrderStatus.READY);
        return this.orderRepository.save(order).toDTO();
    }
}
