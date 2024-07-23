package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import java.util.List;

public interface OrderRepository {

    List<Order> findOrdersByStatus(OrderStatus status);

    Order findById(Long orderId);

    Order startNewOrder(Order order);

    Order addProductToOrder(Order order, Product product, Long productQuantity);

    Order removeProductFromOrder(Order order, Product product);

    Order submitOrder(Order order);

    Order startPreparingOrder(Order order);

    Order finishPreparingOrder(Order order);

    Order deliverOrder(Order order);
}
