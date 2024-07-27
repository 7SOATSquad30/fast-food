package br.com.fiap.grupo30.fastfood.adapters.out.mercadopago;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoPaymentDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoQrCodeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoAdapter {

    @Value("${integrations.mercadopago.base-url}")
    private String baseUrl;

    @Value("${integrations.mercadopago.public-key}")
    private String publicKey;

    @Value("${integrations.mercadopago.access-token}")
    private String privateAccessToken;

    @Value("${integrations.mercadopago.app-user-id}")
    private Long appUserId;

    @Value("${integrations.mercadopago.point-of-sale-id}")
    private String pointOfSaleId;

    @Value("${integrations.mercadopago.notifications-url}")
    private String notificationsUrl;

    @Value("${integrations.mercadopago.payment-collection.user-id}")
    private String userIdForPaymentCollection;

    private final MercadoPagoRequestBuilder requestBuilder;

    public MercadoPagoAdapter(MercadoPagoRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public MercadoPagoQrCodeDTO createQrCodeForPaymentCollection(OrderDTO order) throws Exception {
        String resourceUrlTemplate =
                "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs";
        String resourceUrl = String.format(resourceUrlTemplate, appUserId, pointOfSaleId);

        var requestBody =
                this.requestBuilder.buildQrCodePaymentCollectionRequest(order, notificationsUrl);

        ObjectMapper mapper = new ObjectMapper();
        String serializedRequestBody = mapper.writeValueAsString(requestBody);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(resourceUrl))
                            .header("Authorization", String.format("Bearer %s", privateAccessToken))
                            .header("Content-type", "application/json")
                            .PUT(HttpRequest.BodyPublishers.ofString(serializedRequestBody))
                            .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            MercadoPagoQrCodeDTO result =
                    mapper.readValue(response.body(), MercadoPagoQrCodeDTO.class);

            return result;
        }
    }

    public MercadoPagoPaymentDTO getPayment(String paymentId) throws Exception {
        String resourceUrlTemplate = "https://api.mercadopago.com/v1/payments/%s";
        String resourceUrl = String.format(resourceUrlTemplate, paymentId);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(resourceUrl))
                            .header("Authorization", String.format("Bearer %s", privateAccessToken))
                            .header("Content-type", "application/json")
                            .GET()
                            .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            MercadoPagoPaymentDTO result =
                    new ObjectMapper().readValue(response.body(), MercadoPagoPaymentDTO.class);

            return result;
        }
    }
}
