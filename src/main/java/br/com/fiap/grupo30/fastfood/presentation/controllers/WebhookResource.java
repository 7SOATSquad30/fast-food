package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.payment.CollectOrderPaymentViaMercadoPagoUseCase;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.MercadoPagoGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.events.MercadoPagoAction;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.events.MercadoPagoActionEventDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.MercadoPagoOrderMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/webhooks")
@Tag(name = "Wehooks Resource", description = "RESTful API for webhook events.")
@SuppressWarnings("PMD.InvalidLogMessageFormat")
public class WebhookResource {

    private static final Logger LOG = LoggerFactory.getLogger(WebhookResource.class);

    private final CollectOrderPaymentViaMercadoPagoUseCase collectOrderPaymentMercadoPago;
    private final JpaOrderRepository jpaOrderRepository;
    private final MercadoPagoOrderMapper orderMapper;

    @Autowired
    public WebhookResource(
            CollectOrderPaymentViaMercadoPagoUseCase collectOrderPaymentMercadoPago,
            JpaOrderRepository jpaOrderRepository,
            MercadoPagoOrderMapper orderMapper) {
        this.collectOrderPaymentMercadoPago = collectOrderPaymentMercadoPago;
        this.jpaOrderRepository = jpaOrderRepository;
        this.orderMapper = orderMapper;
    }

    @PostMapping(value = "/mercadopago")
    @Operation(summary = "Handle mercadopago events")
    public ResponseEntity<String> handleMercadoPagoEvent(@RequestBody Map<String, Object> payload)
            throws Exception {

        // TODO: validate request authenticity

        try {
            // topic event
            if (payload.containsKey("topic") && !payload.containsKey("action")) {
                LOG.info("Ignoring topic event from mercadopago", payload);
            }
            // action event
            else if (payload.containsKey("action") && !payload.containsKey("topic")) {
                LOG.info("Received action event from mercadopago", payload);
                MercadoPagoActionEventDTO actionEvent =
                        new ObjectMapper().convertValue(payload, MercadoPagoActionEventDTO.class);
                if (!"payment".equals(actionEvent.getType())) {
                    LOG.info("Ignoring action event from mercadopago", payload);
                    return ResponseEntity.noContent().build();
                }

                this.handlePaymentEvent(actionEvent);
            }
            // unknown event
            else {
                LOG.warn("Ignoring unknown event from mercadopago", payload);
                return ResponseEntity.badRequest().body("Unsupported mercadopago event");
            }

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOG.error("Failed to consume mercadopago event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing event");
        }
    }

    private void handlePaymentEvent(MercadoPagoActionEventDTO event) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        MercadoPagoGateway mercadoPagoGateway = new MercadoPagoGateway(orderMapper);
        if (MercadoPagoAction.PAYMENT_CREATED.getValue().equals(event.getAction())) {
            collectOrderPaymentMercadoPago.execute(orderGateway, mercadoPagoGateway, event);
        } else {
            LOG.warn("Ignoring unimplemented payment event", event);
        }
    }
}
