package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.application.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.application.dto.mercadopago.MercadoPagoQrCodeDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.UniDirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public class PaymentQrCodeDTOMapper
        implements UniDirectionalMapper<MercadoPagoQrCodeDTO, PaymentQrCodeDTO> {
    @Override
    public PaymentQrCodeDTO map(MercadoPagoQrCodeDTO mercadoPagoData) {
        PaymentQrCodeDTO dto = new PaymentQrCodeDTO(mercadoPagoData.getQrData());
        return dto;
    }
}
