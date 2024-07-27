package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.MercadoPagoAdapter;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.PaymentQrCodeDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratePaymentQrCodeUseCase {
    private final JpaOrderRepository orderRepository;
    private final MercadoPagoAdapter mercadoPagoAdapter;
    private final PaymentQrCodeDTOMapper qrCodeMapper;

    @Autowired
    public GeneratePaymentQrCodeUseCase(
            JpaOrderRepository orderRepository,
            MercadoPagoAdapter mercadoPagoAdapter,
            PaymentQrCodeDTOMapper qrCodeMapper) {
        this.orderRepository = orderRepository;
        this.mercadoPagoAdapter = mercadoPagoAdapter;
        this.qrCodeMapper = qrCodeMapper;
    }

    public PaymentQrCodeDTO execute(Long orderId) {
        OrderEntity order =
                this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        MercadoPagoQrCodeDTO qrCodeResponse;
        try {
            qrCodeResponse =
                    this.mercadoPagoAdapter.createQrCodeForPaymentCollection(order.toDTO());
        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not start payment processing", e);
        }

        order.setPaymentProcessing();
        this.orderRepository.save(order);

        return qrCodeMapper.map(qrCodeResponse);
    }
}
