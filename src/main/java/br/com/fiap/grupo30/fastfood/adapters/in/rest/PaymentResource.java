package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.PaymentQrCodeDTO;
import br.com.fiap.grupo30.fastfood.domain.usecases.payment.GeneratePaymentQrCodeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/payments")
@Tag(name = "Payments Resource", description = "RESTful API for managing payments.")
public class PaymentResource {

    private final GeneratePaymentQrCodeUseCase generatePaymentQrCodeUseCase;

    @Autowired
    public PaymentResource(GeneratePaymentQrCodeUseCase generatePaymentQrCodeUseCase) {
        this.generatePaymentQrCodeUseCase = generatePaymentQrCodeUseCase;
    }

    @PostMapping(value = "/{orderId}/qrcode")
    @Operation(summary = "Generate qrcode for order payment collection")
    public ResponseEntity<PaymentQrCodeDTO> generateQrCodeForPaymentCollection(
            @PathVariable Long orderId) {
        PaymentQrCodeDTO qrCode = this.generatePaymentQrCodeUseCase.execute(orderId);
        return ResponseEntity.ok().body(qrCode);
    }
}
