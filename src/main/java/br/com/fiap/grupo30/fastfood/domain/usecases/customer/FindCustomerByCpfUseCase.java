package br.com.fiap.grupo30.fastfood.domain.usecases.customer;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;

public class FindCustomerByCpfUseCase {

    private final CustomerGateway customerGateway;

    public FindCustomerByCpfUseCase(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    public Customer execute(String cpf) {
        return customerGateway.findCustomerByCpf(cpf);
    }
}
