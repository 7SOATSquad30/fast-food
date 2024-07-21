package br.com.fiap.grupo30.fastfood.adapters.out.mercadopago;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoCreateQrCodeForPaymentCollectionResponse;
import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Value("${integrations.mercadopago.payment-collection.user-id}")
    private String userIdForPaymentCollection;

    private final MercadoPagoRequestBuilder requestBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(MercadoPagoAdapter.class);

    public MercadoPagoAdapter(MercadoPagoRequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    public MercadoPagoCreateQrCodeForPaymentCollectionResponse createQrCodeForPaymentCollection(
            OrderDTO order) throws Exception {
        String resourceUrlTemplate =
                "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs";
        String resourceUrl = String.format(resourceUrlTemplate, appUserId, pointOfSaleId);

        LOGGER.info(resourceUrl);

        var requestBody = this.requestBuilder.buildQrCodePaymentCollectionRequest(order, appUserId);

        ObjectMapper mapper = new ObjectMapper();
        String serializedRequestBody = mapper.writeValueAsString(requestBody);

        LOGGER.info(serializedRequestBody);

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(resourceUrl))
                            .header("Authorization", String.format("Bearer %s", privateAccessToken))
                            .header("Conten-type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(serializedRequestBody))
                            .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            MercadoPagoCreateQrCodeForPaymentCollectionResponse result =
                    mapper.readValue(
                            response.body(),
                            MercadoPagoCreateQrCodeForPaymentCollectionResponse.class);

            return result;
        }
    }
}
