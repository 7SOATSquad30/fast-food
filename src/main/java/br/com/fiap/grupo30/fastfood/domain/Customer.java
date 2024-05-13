package br.com.fiap.grupo30.fastfood.domain;

import br.com.fiap.grupo30.fastfood.domain.vo.CPF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Customer {
    private Long id;
    private String name;
    private CPF cpf;
    private String email;
}
