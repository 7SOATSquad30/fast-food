package br.com.fiap.grupo30.fastfood.application.dto;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private OrderStatus status;
    private OrderItemDTO[] items;
    private Double totalPrice;
    private CustomerDTO customer;
}
