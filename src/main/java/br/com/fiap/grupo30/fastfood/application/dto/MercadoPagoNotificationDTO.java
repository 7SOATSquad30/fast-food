package br.com.fiap.grupo30.fastfood.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoNotificationDTO {
    private String action;
    private String type;

    @JsonProperty("date_created")
    private Date dateCreated;

    private String id;
    private Object data;
}
