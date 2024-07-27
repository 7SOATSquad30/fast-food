package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import java.util.List;

public interface OrderRepository {

    List<Order> findOrdersWithSpecificStatuses();

    List<Order> findOrdersByStatus(OrderStatus status);

    Order findById(Long orderId);

    Order findByIdForUpdate(Long orderId);

    Order save(Order order);
}
