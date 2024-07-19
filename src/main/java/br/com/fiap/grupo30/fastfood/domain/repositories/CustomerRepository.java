package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;

public interface CustomerRepository {

    CustomerDTO findCustomerByCpf(String cpf);

    CustomerDTO insert(CustomerDTO dto);
}
