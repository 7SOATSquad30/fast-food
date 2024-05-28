package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.InvalidOrderStatusException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ListOrdersUseCase {

    private final OrderRepository orderRepository;

    @Autowired
    public ListOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderDTO> execute(String status) {
        OrderStatus orderStatus = null;
        if (status != null && !status.isEmpty()) {
            try {
                orderStatus = OrderStatus.valueOf(status.toUpperCase(Locale.ENGLISH));
            } catch (IllegalArgumentException e) {
                throw new InvalidOrderStatusException(status, e);
            }
        }
        return orderRepository.findOrdersByStatus(orderStatus).stream()
                .sorted(Comparator.comparing(OrderEntity::getCreatedAt))
                .map(OrderEntity::toDTO)
                .toList();
    }
}
