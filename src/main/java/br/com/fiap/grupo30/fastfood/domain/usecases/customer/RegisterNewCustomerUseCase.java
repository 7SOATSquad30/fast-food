package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.domain.services.CustomerService;
import org.springframework.stereotype.Component;

@Component
public class RegisterNewCustomerUseCase {

    private final CustomerService customerService;

    public RegisterNewCustomerUseCase(CustomerService customerService) {
        this.customerService = customerService;
    }

    public CustomerDTO execute(CustomerDTO dto) {
        return customerService.insert(dto);
    }
}
