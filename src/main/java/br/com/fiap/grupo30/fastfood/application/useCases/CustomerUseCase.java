package br.com.fiap.grupo30.fastfood.application.useCases;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;

public interface CustomerUseCase {

    CustomerDTO findCustomerByCpf(String cpf);

    CustomerDTO createCustomer(CustomerDTO dto);
}
