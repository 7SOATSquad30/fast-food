package br.com.fiap.grupo30.fastfood.application.useCases.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.services.CustomerService;
import br.com.fiap.grupo30.fastfood.application.useCases.CustomerUseCase;
import org.springframework.stereotype.Service;

@Service
public class CustomerUseCaseImpl implements CustomerUseCase {

    private final CustomerService customerService;

    public CustomerUseCaseImpl(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public CustomerDTO findCustomerByCpf(String cpf) {
        return customerService.findCustomerByCpf(cpf);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO dto) {
        return customerService.insert(dto);
    }
}
