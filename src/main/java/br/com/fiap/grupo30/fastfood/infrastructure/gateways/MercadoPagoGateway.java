package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoOrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoPaymentDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.MercadoPagoOrderMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MercadoPagoGateway {
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

    private ObjectMapper jsonMapper;
    private MercadoPagoOrderMapper orderMapper;

    @Autowired
    public MercadoPagoGateway(MercadoPagoOrderMapper orderMapper) {
        this.jsonMapper = new ObjectMapper();
        this.orderMapper = orderMapper;
    }

    private Map<String, String> getHeaders() {
        return new HashMap<String, String>() {
            {
                put("Authorization", String.format("Bearer %s", privateAccessToken));
                put("Content-type", "application/json");
            }
        };
    }

    private HttpResponse<String> makeRequest(String httpMethod, URI resourceUri) throws Exception {
        return makeRequest(httpMethod, resourceUri, BodyPublishers.noBody());
    }

    private HttpResponse<String> makeRequest(String httpMethod, URI resourceUri, BodyPublisher body)
            throws Exception {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest.Builder builder =
                    HttpRequest.newBuilder().uri(resourceUri).method(httpMethod, body);
            getHeaders().forEach(builder::header);

            HttpRequest request = builder.build();

            return client.send(request, HttpResponse.BodyHandlers.ofString());
        }
    }

    public MercadoPagoQrCodeDTO createQrCodeForOrderPaymentCollection(OrderDTO order)
            throws Exception {
        String resourceUriTemplate =
                "https://api.mercadopago.com/instore/orders/qr/seller/collectors/%s/pos/%s/qrs";
        URI resourceUri = URI.create(String.format(resourceUriTemplate, appUserId, pointOfSaleId));

        MercadoPagoOrderDTO mercadoPagoOrder = orderMapper.map(order, notificationsUrl);
        BodyPublisher requestBody =
                HttpRequest.BodyPublishers.ofString(
                        jsonMapper.writeValueAsString(mercadoPagoOrder));

        var response = makeRequest("PUT", resourceUri, requestBody);
        return jsonMapper.readValue(response.body(), MercadoPagoQrCodeDTO.class);
    }

    public MercadoPagoPaymentDTO getPaymentState(String paymentId) throws Exception {
        String resourceUriTemplate = "https://api.mercadopago.com/v1/payments/%s";
        URI resourceUri = URI.create(String.format(resourceUriTemplate, paymentId));

        var response = makeRequest("GET", resourceUri);
        return jsonMapper.readValue(response.body(), MercadoPagoPaymentDTO.class);
    }
}
