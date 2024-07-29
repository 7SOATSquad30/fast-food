package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderItemDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoCashOutDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoOrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoOrderItemDTO;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoOrderMapper {
    public MercadoPagoOrderDTO map(OrderDTO order, String notificationsUrl) {
        String orderId = order.getOrderId().toString();
        MercadoPagoOrderDTO mercadoPagoOrder = new MercadoPagoOrderDTO();
        mercadoPagoOrder.setTitle(String.format("Pedido %s", orderId));
        mercadoPagoOrder.setDescription(String.format("Pedido da lanchonete fastfood"));
        mercadoPagoOrder.setExpirationDate(Date.from(Instant.now().plusSeconds(3600)));
        mercadoPagoOrder.setExternalReference(orderId);
        mercadoPagoOrder.setTotalAmount(order.getTotalPrice());
        mercadoPagoOrder.setNotificationUrl(notificationsUrl);

        MercadoPagoCashOutDTO cashOut = new MercadoPagoCashOutDTO();
        cashOut.setAmount(order.getTotalPrice());

        MercadoPagoOrderItemDTO[] items =
                Stream.of(order.getItems())
                        .map(this::mapOrderItem)
                        .toArray(MercadoPagoOrderItemDTO[]::new);
        mercadoPagoOrder.setItems(items);

        return mercadoPagoOrder;
    }

    private MercadoPagoOrderItemDTO mapOrderItem(OrderItemDTO orderItem) {
        MercadoPagoOrderItemDTO mercadoPagoOrderItem = new MercadoPagoOrderItemDTO();
        mercadoPagoOrderItem.setTitle(orderItem.getProduct().getName());
        mercadoPagoOrderItem.setDescription(orderItem.getProduct().getDescription());
        mercadoPagoOrderItem.setCategory(orderItem.getProduct().getCategory());
        mercadoPagoOrderItem.setQuantity(orderItem.getQuantity());
        mercadoPagoOrderItem.setUnitPrice(orderItem.getProduct().getPrice());
        mercadoPagoOrderItem.setUnitMeasure("unit");
        mercadoPagoOrderItem.setTotalAmount(orderItem.getTotalPrice());
        return mercadoPagoOrderItem;
    }
}
