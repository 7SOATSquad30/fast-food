package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OrderEntityMapper implements BiDirectionalMapper<Order, OrderEntity> {
    private final CustomerEntityMapper customerEntityMapper;
    private final ProductEntityMapper productEntityMapper;

    @Autowired
    public OrderEntityMapper(
            CustomerEntityMapper customerEntityMapper, ProductEntityMapper productEntityMapper) {
        this.customerEntityMapper = customerEntityMapper;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public OrderEntity mapTo(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        updateEntityFromOrder(entity, order);
        return entity;
    }

    @Override
    public Order mapFrom(OrderEntity entity) {
        Order order = new Order();
        order.setId(entity.getId());
        order.setStatus(entity.getStatus());
        order.setCustomer(customerEntityMapper.mapFrom(entity.getCustomer()));
        entity.getItems()
                .forEach(
                        itemEntity ->
                                order.addProduct(
                                        productEntityMapper.mapFrom(itemEntity.getProduct()),
                                        itemEntity.getQuantity()));
        return order;
    }

    public void updateEntityFromOrder(OrderEntity entity, Order order) {
        entity.setStatus(order.getStatus());
        entity.setCustomer(customerEntityMapper.mapTo(order.getCustomer()));
        entity.setStatus(order.getStatus());
        entity.getItems().clear();
        order.getItems()
                .forEach(
                        item ->
                                entity.addProduct(
                                        productEntityMapper.mapTo(item.getProduct()),
                                        item.getQuantity()));
    }
}
