package br.com.fiap.grupo30.fastfood.application.services;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;

public interface CustomerService {

    CustomerDTO findCustomerByCpf(String cpf);

    CustomerDTO insert(CustomerDTO dto);
}
