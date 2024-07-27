package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentDTO {
    private PaymentStatus status;
    private Double amount;
}
