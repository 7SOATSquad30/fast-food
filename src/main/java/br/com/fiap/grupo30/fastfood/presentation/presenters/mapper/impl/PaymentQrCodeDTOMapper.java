package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.MercadoPagoQrCodeDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.UniDirectionalMapper;
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
