package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OrderDTOMapper implements Mapper<Order, OrderDTO> {

    private final CustomerDTOMapper customerDTOMapper;
    private final ProductDTOMapper productDTOMapper;

    @Autowired
    public OrderDTOMapper(CustomerDTOMapper customerDTOMapper, ProductDTOMapper productDTOMapper) {
        this.customerDTOMapper = customerDTOMapper;
        this.productDTOMapper = productDTOMapper;
    }

    @Override
    public OrderDTO mapTo(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getItems().stream()
                        .map(
                                orderItem ->
                                        new OrderItemDTO(
                                                orderItem.getQuantity(),
                                                orderItem.getTotalPrice(),
                                                productDTOMapper.mapTo(orderItem.getProduct())))
                        .toArray(OrderItemDTO[]::new),
                order.getTotalPrice(),
                customerDTOMapper.mapTo(order.getCustomer()));
    }

    @Override
    public Order mapFrom(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getOrderId());
        order.setStatus(dto.getStatus());
        order.setCustomer(customerDTOMapper.mapFrom(dto.getCustomer()));
        for (OrderItemDTO orderItemDTO : dto.getItems()) {
            order.addProduct(
                    productDTOMapper.mapFrom(orderItemDTO.getProduct()),
                    orderItemDTO.getQuantity());
        }
        return order;
    }
}
