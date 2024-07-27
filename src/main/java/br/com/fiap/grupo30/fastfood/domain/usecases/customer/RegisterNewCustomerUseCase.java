package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;

public class RegisterNewCustomerUseCase {

    private final CustomerGateway customerGateway;

    public RegisterNewCustomerUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Customer execute(Customer customer) {
        return customerGateway.insert(customer);
    }
}
