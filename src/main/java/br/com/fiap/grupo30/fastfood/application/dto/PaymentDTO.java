package br.com.fiap.grupo30.fastfood.application.dto;

import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDTO {
    private PaymentStatus status;
    private Double amount;
}
