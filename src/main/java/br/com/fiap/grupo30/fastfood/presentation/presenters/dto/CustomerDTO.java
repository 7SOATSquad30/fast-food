package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.domain.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Campo obrigatório")
    private String name;

    @NotBlank(message = "Campo obrigatório")
    private String cpf;

    @Email(message = "Favor entrar um email válido")
    private String email;

    public CustomerDTO(Customer entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.cpf = String.valueOf(entity.getCpf().value());
        this.email = entity.getEmail();
    }
}
