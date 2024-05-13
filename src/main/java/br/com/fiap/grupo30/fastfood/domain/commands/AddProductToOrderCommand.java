package br.com.fiap.grupo30.fastfood.domain.commands;

import lombok.Data;

@Data
public class AddProductToOrderCommand {
    private Long orderId;
    private Long productId;
    private Long productQuantity;
}
