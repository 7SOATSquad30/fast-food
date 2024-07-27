package br.com.fiap.grupo30.fastfood.presentation.presenters.dto.mercadopago.events;

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
public class MercadoPagoActionEventDTO {
    private String id;
    private String action;
    private String type;
    private MercadoPagoActionEventDataDTO data;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("date_created")
    private Date dateCreated;

    @JsonProperty("live_mode")
    private Boolean liveMode;
}
