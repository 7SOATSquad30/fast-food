package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.InvalidCpfException;

public class FindCustomerByCpfUseCase {

    private final CustomerGateway customerGateway;

    public FindCustomerByCpfUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public CustomerDTO execute(String cpf) {
        if (!CPF.isValid(cpf)) {
            throw new InvalidCpfException(cpf);
        }

        return customerGateway.findCustomerByCpf(CPF.removeNonDigits(cpf)).toDTO();
    }
}
