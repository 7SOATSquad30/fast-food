package br.com.fiap.grupo30.fastfood.domain.services;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO findCustomerByCpf(String cpf);

    CustomerDTO insert(CustomerDTO dto);
}
