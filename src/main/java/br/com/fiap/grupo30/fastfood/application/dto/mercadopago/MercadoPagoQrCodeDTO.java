package br.com.fiap.grupo30.fastfood.application.dto.mercadopago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoQrCodeDTO {
    @JsonProperty("qr_data")
    private String qrData;

    @JsonProperty("in_store_order_id")
    private String inStoreOrderId;
}
