package br.com.fiap.grupo30.fastfood.adapters.out.mercadopago;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoCashOutDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoCreateQrCodeForPaymentCollectionRequestDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoOrderItemDTO;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoRequestBuilder {
    public MercadoPagoCreateQrCodeForPaymentCollectionRequestDTO
            buildQrCodePaymentCollectionRequest(OrderDTO order, String notificationsUrl) {
        String orderId = order.getOrderId().toString();
        MercadoPagoCreateQrCodeForPaymentCollectionRequestDTO requestBody =
                new MercadoPagoCreateQrCodeForPaymentCollectionRequestDTO();
        requestBody.setTitle(String.format("Pedido %s", orderId));
        requestBody.setDescription(String.format("Pedido da lanchonete fastfood"));
        requestBody.setExpirationDate(Date.from(Instant.now().plusSeconds(3600)));
        requestBody.setExternalReference(orderId);
        requestBody.setTotalAmount(order.getTotalPrice());
        requestBody.setNotificationUrl(notificationsUrl);

        MercadoPagoCashOutDTO cashOut = new MercadoPagoCashOutDTO();
        cashOut.setAmount(order.getTotalPrice());

        MercadoPagoOrderItemDTO[] items =
                Stream.of(order.getItems())
                        .map(
                                (ourOrderItem) -> {
                                    MercadoPagoOrderItemDTO theirOrderItem =
                                            new MercadoPagoOrderItemDTO();
                                    theirOrderItem.setTitle(ourOrderItem.getProduct().getName());
                                    theirOrderItem.setDescription(
                                            ourOrderItem.getProduct().getDescription());
                                    theirOrderItem.setCategory(
                                            ourOrderItem.getProduct().getCategory().getName());
                                    theirOrderItem.setQuantity(ourOrderItem.getQuantity());
                                    theirOrderItem.setUnitPrice(
                                            ourOrderItem.getProduct().getPrice());
                                    theirOrderItem.setUnitMeasure("unit");
                                    theirOrderItem.setTotalAmount(ourOrderItem.getTotalPrice());
                                    return theirOrderItem;
                                })
                        .toArray(MercadoPagoOrderItemDTO[]::new);
        requestBody.setItems(items);

        return requestBody;
    }
}
