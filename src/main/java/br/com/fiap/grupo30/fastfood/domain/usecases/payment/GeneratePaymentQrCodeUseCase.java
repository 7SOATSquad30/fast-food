package br.com.fiap.grupo30.fastfood.domain.usecases.payment;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.MercadoPagoAdapter;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.PaymentProcessingFailedException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.PaymentQrCodeDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeneratePaymentQrCodeUseCase {
    private final OrderGateway orderGateway;
    private final MercadoPagoAdapter mercadoPagoAdapter;
    private final PaymentQrCodeDTOMapper qrCodeMapper;

    @Autowired
    public GeneratePaymentQrCodeUseCase(
            OrderGateway orderGateway,
            MercadoPagoAdapter mercadoPagoAdapter,
            PaymentQrCodeDTOMapper qrCodeMapper) {
        this.orderGateway = orderGateway;
        this.mercadoPagoAdapter = mercadoPagoAdapter;
        this.qrCodeMapper = qrCodeMapper;
    }

    public PaymentQrCodeDTO execute(Long orderId) {
        Order order = this.orderGateway.findById(orderId);

        MercadoPagoQrCodeDTO qrCodeResponse;
        try {
            qrCodeResponse =
                    this.mercadoPagoAdapter.createQrCodeForPaymentCollection(order.toDTO());
        } catch (Exception e) {
            throw new PaymentProcessingFailedException("Could not start payment processing", e);
        }

        order.setPaymentProcessing();
        this.orderGateway.save(order);

        return qrCodeMapper.map(qrCodeResponse);
    }
}
