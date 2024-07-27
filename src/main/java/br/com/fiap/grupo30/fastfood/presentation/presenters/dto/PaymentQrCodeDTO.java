package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentQrCodeDTO {
    public String qrCodeData;
}
