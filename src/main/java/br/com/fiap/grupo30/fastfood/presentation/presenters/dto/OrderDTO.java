package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
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
    private PaymentDTO payment;
}
