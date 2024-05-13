package br.com.fiap.grupo30.fastfood.application.dto;

import lombok.Data;

@Data
public class AddOrderProductRequest {
    private final Long productId;
    private final Long quantity;
}
