package br.com.fiap.grupo30.fastfood.application.dto;

import br.com.fiap.grupo30.fastfood.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String name;
    private String cpf;
    private String email;

    public CustomerDTO(Customer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = String.valueOf(entity.getCpf().value());
        this.email = entity.getEmail();
    }
}
