package br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MercadoPagoCreateQrCodeForPaymentCollectionResponse {
    private String qr_data;
    private String in_store_order_id;
}
