package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.domain.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.OrderEntityMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderGateway implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Autowired
    public OrderGateway(
            JpaOrderRepository jpaOrderRepository, OrderEntityMapper orderEntityMapper) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    public List<Order> findOrdersByStatus(OrderStatus orderStatus) {
        return jpaOrderRepository.findOrdersByStatus(orderStatus).stream()
                .sorted(Comparator.comparing(OrderEntity::getCreatedAt))
                .map(orderEntityMapper::mapFrom)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Order findById(Long orderId) {
        OrderEntity entity =
                jpaOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return orderEntityMapper.mapFrom(entity);
    }

    @Override
    @Transactional
    public Order startNewOrder(Order order) {
        OrderEntity entity = jpaOrderRepository.save(orderEntityMapper.mapTo(order));
        return orderEntityMapper.mapFrom(entity);
    }

    @Override
    @Transactional
    public Order addProductToOrder(Order order, Product product, Long productQuantity) {
        order.addProduct(product, productQuantity);
        return saveUpdatedOrder(order);
    }

    @Override
    @Transactional
    public Order removeProductFromOrder(Order order, Product product) {
        order.removeProduct(product);
        return saveUpdatedOrder(order);
    }

    @Override
    @Transactional
    public Order submitOrder(Order order) {
        return saveUpdatedOrder(order);
    }

    @Override
    @Transactional
    public Order startPreparingOrder(Order order) {
        return saveUpdatedOrder(order);
    }

    @Override
    @Transactional
    public Order finishPreparingOrder(Order order) {
        return saveUpdatedOrder(order);
    }

    @Override
    @Transactional
    public Order deliverOrder(Order order) {
        return saveUpdatedOrder(order);
    }

    private Order saveUpdatedOrder(Order order) {
        try {
            OrderEntity entity = jpaOrderRepository.getReferenceById(order.getId());
            orderEntityMapper.updateEntityFromOrder(entity, order);
            entity = jpaOrderRepository.save(entity);
            return orderEntityMapper.mapFrom(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + order.getId(), e);
        }
    }
}
