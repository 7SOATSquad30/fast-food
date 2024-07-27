package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Payment;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.PaymentDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public final class PaymentDTOMapper implements BiDirectionalMapper<Payment, PaymentDTO> {
    @Override
    public PaymentDTO mapTo(Payment payment) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(payment.getId());
        dto.setStatus(payment.getStatus());
        dto.setAmount(payment.getAmount());
        return dto;
    }

    @Override
    public Payment mapFrom(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setId(dto.getId());
        payment.setStatus(dto.getStatus());
        payment.setAmount(dto.getAmount());
        return payment;
    }
}
