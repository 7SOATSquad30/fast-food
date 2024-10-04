package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.configuration.Constants;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.InvalidCpfException;

public class StartNewOrderUseCase {

    public OrderDTO execute(
            OrderGateway orderGateway, CustomerGateway customerGateway, String customerCpf) {
        if (!CPF.isValid(customerCpf)) {
            throw new InvalidCpfException(customerCpf);
        }

        Customer customer = findCustomerOrCreateAnonymous(customerGateway, new CPF(customerCpf));
        Order newOrder = Order.createFor(customer);
        return orderGateway.save(newOrder).toDTO();
    }

    private Customer findCustomerOrCreateAnonymous(
            CustomerGateway customerGateway, CPF customerCpf) {
        if (customerCpf != null) {
            return customerGateway.findCustomerByCpf(customerCpf.value());
        } else {
            Customer anonymousCustomer = customerGateway.findCustomerByCpf(Constants.ANONYMOUS_CPF);
            if (anonymousCustomer != null) {
                return anonymousCustomer;
            } else {
                Customer newAnonymousCustomer =
                        Customer.create(
                                "Anonymous", Constants.ANONYMOUS_CPF, "anonymous@fastfood.com");
                return customerGateway.save(newAnonymousCustomer);
            }
        }
    }
}
