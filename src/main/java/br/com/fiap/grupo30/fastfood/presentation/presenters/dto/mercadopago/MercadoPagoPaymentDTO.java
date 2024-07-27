package br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class MercadoPagoPaymentDTO {
    private Long id;

    @JsonProperty("date_created")
    private Date dateCreated;

    @JsonProperty("date_approved")
    private Date dateApproved;

    @JsonProperty("date_last_updated")
    private Date dateLastUpdated;

    @JsonProperty("money_release_date")
    private Date moneyReleaseDate;

    @JsonProperty("payment_method_id")
    private String paymentMethodId;

    @JsonProperty("payment_type_id")
    private String paymentTypeId;

    private String status;

    @JsonProperty("status_detail")
    private String statusDetail;

    @JsonProperty("currency_id")
    private String currencyId;

    private String description;

    @JsonProperty("collector_id")
    private Long collectorId;

    @JsonProperty("external_reference")
    private String externalReference;

    @JsonProperty("transaction_amount")
    private Double transactionAmount;

    @JsonProperty("transaction_amount_refunded")
    private Double transactionAmountRefunded;

    @JsonProperty("coupon_amount")
    private Double couponAmount;

    private Long installments;
    // private Map<String, Object> transaction_details;
    // private Map<String, Object> payer;
    // private Map<String, Object> metadata;
    // private Map<String, Object> additional_info;
    // private Map<String, Object> card;
}
