package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MercadoPagoNotificationDTO {
    private String action;
    private String type;

    @JsonProperty("date_created")
    private Date dateCreated;

    private String id;
    private Object data;
}
