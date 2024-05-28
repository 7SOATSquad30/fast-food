package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import org.springframework.stereotype.Component;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.services.CustomerService;

@Component
public class FindCustomerByCpfUseCase {

    private final CustomerService customerService;

    public FindCustomerByCpfUseCase(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDTO execute(String cpf) {
        return customerService.findCustomerByCpf(cpf);
    }

}
