package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.adapters.out.mercadopago.model.MercadoPagoCreateQrCodeForPaymentCollectionResponse;
import br.com.fiap.grupo30.fastfood.application.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.UniDirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentQrCodeDTOMapper
        implements UniDirectionalMapper<
                MercadoPagoCreateQrCodeForPaymentCollectionResponse, PaymentQrCodeDTO> {
    @Override
    public PaymentQrCodeDTO map(
            MercadoPagoCreateQrCodeForPaymentCollectionResponse mercadoPagoData) {
        PaymentQrCodeDTO dto = new PaymentQrCodeDTO(mercadoPagoData.getQr_data());
        return dto;
    }
}
