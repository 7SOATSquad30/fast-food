package br.com.fiap.grupo30.fastfood.adapters.out.mercadopago;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoCashOut;
import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoCreateQrCodeForPaymentCollectionRequest;
import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoOrderItem;
import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoSponsor;
import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import java.time.Instant;
import java.util.Date;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoRequestBuilder {
    public MercadoPagoCreateQrCodeForPaymentCollectionRequest buildQrCodePaymentCollectionRequest(
            OrderDTO order, Long mercadoPagoAppUserId) {
        String orderId = order.getOrderId().toString();
        MercadoPagoCreateQrCodeForPaymentCollectionRequest requestBody =
                new MercadoPagoCreateQrCodeForPaymentCollectionRequest();
        requestBody.setTitle(String.format("Pedido %s", orderId));
        requestBody.setDescription(String.format("Pedido da lanchonete fastfood"));
        requestBody.setExpirationDate(Date.from(Instant.now().plusSeconds(3600)));
        requestBody.setExternalReference(orderId);
        requestBody.setTotalAmount(order.getTotalPrice());

        MercadoPagoCashOut cashOut = new MercadoPagoCashOut();
        cashOut.setAmount(order.getTotalPrice());

        MercadoPagoOrderItem[] items =
                Stream.of(order.getItems())
                        .map(
                                (ourOrderItem) -> {
                                    MercadoPagoOrderItem theirOrderItem =
                                            new MercadoPagoOrderItem();
                                    theirOrderItem.setTitle(ourOrderItem.getProduct().getName());
                                    theirOrderItem.setDescription(
                                            ourOrderItem.getProduct().getDescription());
                                    theirOrderItem.setCategory(
                                            ourOrderItem.getProduct().getCategory().getName());
                                    theirOrderItem.setQuantity(ourOrderItem.getQuantity());
                                    theirOrderItem.setUnitPrice(
                                            ourOrderItem.getProduct().getPrice());
                                    theirOrderItem.setUnitMeasure("unit");
                                    theirOrderItem.setTotalAmount(
                                            ourOrderItem.getTotalPrice());
                                    return theirOrderItem;
                                })
                        .toArray(MercadoPagoOrderItem[]::new);
        requestBody.setItems(items);

        return requestBody;
    }
}
