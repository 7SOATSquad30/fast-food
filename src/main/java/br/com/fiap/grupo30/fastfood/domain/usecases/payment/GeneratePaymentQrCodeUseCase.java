package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.MercadoPagoAdapter;
import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoCreateQrCodeForPaymentCollectionResponse;
import br.com.fiap.grupo30.fastfood.application.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.PaymentQrCodeDTOMapper;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.PaymentStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratePaymentQrCodeUseCase {
    private final OrderRepository orderRepository;
    private final MercadoPagoAdapter mercadoPagoAdapter;
    private final PaymentQrCodeDTOMapper qrCodeMapper;

    @Autowired
    public GeneratePaymentQrCodeUseCase(
            OrderRepository orderRepository,
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

        MercadoPagoCreateQrCodeForPaymentCollectionResponse qrCodeResponse;
        try {
            qrCodeResponse =
                    this.mercadoPagoAdapter.createQrCodeForPaymentCollection(order.toDTO());
        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not start payment processing", e);
        }

        order.updatePaymentStatus(PaymentStatus.PROCESSING);
        this.orderRepository.save(order);

        return qrCodeMapper.map(qrCodeResponse);
    }
}
