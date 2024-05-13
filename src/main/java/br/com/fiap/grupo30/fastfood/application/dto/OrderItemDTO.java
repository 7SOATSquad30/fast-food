package br.com.fiap.grupo30.fastfood.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long quantity;
    private Double totalPrice;
    private ProductDTO product;
}
