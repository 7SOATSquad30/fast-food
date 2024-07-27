package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.domain.repositories.CustomerRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class FindCustomerByCpfUseCase {

    private final CustomerRepository customerRepository;

    public FindCustomerByCpfUseCase(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDTO execute(String cpf) {
        return customerRepository.findCustomerByCpf(cpf);
    }
}
