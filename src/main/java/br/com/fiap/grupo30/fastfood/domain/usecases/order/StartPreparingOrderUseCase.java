package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderStatusPreparingOtherThanSubmittedException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartPreparingOrderUseCase {

    private final JpaOrderRepository jpaOrderRepository;

    @Autowired
    public StartPreparingOrderUseCase(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    public OrderDTO execute(Long orderId) {
        OrderEntity order =
                this.jpaOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() != OrderStatus.SUBMITTED) {
            throw new CantChangeOrderStatusPreparingOtherThanSubmittedException();
        }

        order.setStatus(OrderStatus.PREPARING);
        return this.jpaOrderRepository.save(order).toDTO();
    }
}
