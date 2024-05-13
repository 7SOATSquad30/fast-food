package br.com.fiap.grupo30.fastfood.domain.commands;

import lombok.Data;

@Data
public class RemoveProductFromOrderCommand {
    private Long orderId;
    private Long productId;
}
