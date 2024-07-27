package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class OrderDTOMapper implements BiDirectionalMapper<Order, OrderDTO> {

    private final CustomerDTOMapper customerDTOMapper;
    private final ProductDTOMapper productDTOMapper;
    private final PaymentDTOMapper paymentDTOMapper;

    @Autowired
    public OrderDTOMapper(
            CustomerDTOMapper customerDTOMapper,
            ProductDTOMapper productDTOMapper,
            PaymentDTOMapper paymentDTOMapper) {
        this.customerDTOMapper = customerDTOMapper;
        this.productDTOMapper = productDTOMapper;
        this.paymentDTOMapper = paymentDTOMapper;
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
                customerDTOMapper.mapTo(order.getCustomer()),
                paymentDTOMapper.mapTo(order.getPayment()));
    }

    @Override
    public Order mapFrom(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getOrderId());
        order.setStatus(dto.getStatus());
        order.setCustomer(customerDTOMapper.mapFrom(dto.getCustomer()));
        order.setPayment(paymentDTOMapper.mapFrom(dto.getPayment()));
        for (OrderItemDTO orderItemDTO : dto.getItems()) {
            order.addProduct(
                    productDTOMapper.mapFrom(orderItemDTO.getProduct()),
                    orderItemDTO.getQuantity());
        }
        return order;
    }
}
