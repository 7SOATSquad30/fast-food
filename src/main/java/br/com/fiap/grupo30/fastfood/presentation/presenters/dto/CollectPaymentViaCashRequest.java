package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectPaymentViaCashRequest {
    private Double amount;
}
