package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.InvalidCpfException;

public class RegisterNewCustomerUseCase {

    public CustomerDTO execute(
            CustomerGateway customerGateway, String name, String cpf, String email) {
        if (!CPF.isValid(cpf)) {
            throw new InvalidCpfException(cpf);
        }

        Customer customer = Customer.create(name, cpf, email);
        return customerGateway.save(customer).toDTO();
    }
}
