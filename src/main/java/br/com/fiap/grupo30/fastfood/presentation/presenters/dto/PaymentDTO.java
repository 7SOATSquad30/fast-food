package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private Long id;
    private PaymentStatus status;
    private Double amount;
}
