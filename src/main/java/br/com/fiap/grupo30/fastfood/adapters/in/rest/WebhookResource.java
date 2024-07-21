package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/webhooks")
@Tag(name = "Wehooks Resource", description = "RESTful API for webhook events.")
public class WebhookResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebhookResource.class);

    @PostMapping(value = "/mercadopago")
    @Operation(summary = "Handle mercadopago events")
    public ResponseEntity createCustomer(@RequestBody @Valid HashMap notification)
            throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String serializedRequestBody = mapper.writeValueAsString(notification);
        LOGGER.info(serializedRequestBody);
        return ResponseEntity.noContent().build();
    }
}
