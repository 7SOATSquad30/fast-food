package br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoCreateQrCodeForPaymentCollectionRequest {
    private String title;
    private String description;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("expiration_date")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            timezone = "GMT")
    private Date expirationDate;

    private MercadoPagoSponsor sponsor;
    private MercadoPagoOrderItem[] items;

    @JsonProperty("cash_out")
    private MercadoPagoCashOut cashOut;

    @JsonProperty("total_amount")
    private Double totalAmount;

    @JsonProperty("notification_url")
    private String notificationUrl;
}
