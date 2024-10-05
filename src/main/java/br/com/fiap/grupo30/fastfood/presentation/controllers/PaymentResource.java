package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.payment.CollectOrderPaymentViaCashUseCase;
import br.com.fiap.grupo30.fastfood.domain.usecases.payment.GeneratePaymentQrCodeUseCase;
import br.com.fiap.grupo30.fastfood.infrastructure.auth.AdminRequired;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CollectPaymentViaCashRequest;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.MercadoPagoOrderMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
@Tag(name = "Payments Resource", description = "RESTful API for managing payments.")
public class PaymentResource {

    private final GeneratePaymentQrCodeUseCase generatePaymentQrCodeUseCase;
    private final CollectOrderPaymentViaCashUseCase collectOrderPaymentViaCashUseCase;
    private final JpaOrderRepository jpaOrderRepository;
    private final MercadoPagoOrderMapper orderMapper;

    @Autowired
    public PaymentResource(
            GeneratePaymentQrCodeUseCase generatePaymentQrCodeUseCase,
            CollectOrderPaymentViaCashUseCase collectOrderPaymentViaCashUseCase,
            JpaOrderRepository jpaOrderRepository,
            MercadoPagoOrderMapper orderMapper) {
        this.generatePaymentQrCodeUseCase = generatePaymentQrCodeUseCase;
        this.collectOrderPaymentViaCashUseCase = collectOrderPaymentViaCashUseCase;
        this.jpaOrderRepository = jpaOrderRepository;
        this.orderMapper = orderMapper;
    }

    @PostMapping(value = "/{orderId}/qrcode")
    @Operation(summary = "Generate qrcode for order payment collection")
    public ResponseEntity<PaymentQrCodeDTO> generateQrCodeForPaymentCollection(
            @PathVariable Long orderId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        MercadoPagoGateway mercadoPagoGateway = new MercadoPagoGateway(orderMapper);
        PaymentQrCodeDTO qrCode =
                this.generatePaymentQrCodeUseCase.execute(
                        orderGateway, mercadoPagoGateway, orderId);
        return ResponseEntity.ok().body(qrCode);
    }

    @AdminRequired()
    @PostMapping(value = "/{orderId}/collect")
    @Operation(summary = "Collect payment by cash")
    public ResponseEntity<OrderDTO> collectPaymentByBash(
            @PathVariable Long orderId, @RequestBody CollectPaymentViaCashRequest request) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        OrderDTO order =
                this.collectOrderPaymentViaCashUseCase.execute(
                        orderGateway, orderId, request.getAmount());
        return ResponseEntity.ok().body(order);
    }
}
