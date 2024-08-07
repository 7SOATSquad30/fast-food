package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemDTO {
    private ProductDTO product;
    private Long quantity;
    private Double totalPrice;
}
