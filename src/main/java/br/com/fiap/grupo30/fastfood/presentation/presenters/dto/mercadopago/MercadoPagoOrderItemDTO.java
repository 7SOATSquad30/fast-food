package br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoOrderItemDTO {
    @JsonProperty("sku_number")
    private String skuNumber;

    private String category;
    private String title;
    private String description;

    @JsonProperty("unit_price")
    private Double unitPrice;

    private Long quantity;

    @JsonProperty("unit_measure")
    private String unitMeasure;

    @JsonProperty("total_amount")
    private Double totalAmount;
}
