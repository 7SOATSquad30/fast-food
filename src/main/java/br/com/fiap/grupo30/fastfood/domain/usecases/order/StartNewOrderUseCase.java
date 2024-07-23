package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.configuration.Constants;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;

public class StartNewOrderUseCase {

    private final OrderGateway orderGateway;
    private final CustomerGateway customerGateway;

    public StartNewOrderUseCase(OrderGateway orderGateway, CustomerGateway customerGateway) {
        this.orderGateway = orderGateway;
        this.customerGateway = customerGateway;
    }

    public Order execute(Customer domainObj) {
        Customer customer = findOrCreateAnonymousCustomer(domainObj);
        Order newOrder = new Order();
        newOrder.setCustomer(customer);
        return orderGateway.startNewOrder(newOrder);
    }

    private Customer findOrCreateAnonymousCustomer(Customer domainObj) {
        if (domainObj != null) {
            return customerGateway.findCustomerByCpf(domainObj.getCpf().value());
        } else {
            Customer anonymousCustomer = customerGateway.findCustomerByCpf(Constants.ANONYMOUS_CPF);
            if (anonymousCustomer != null) {
                return anonymousCustomer;
            } else {
                Customer newAnonymousCustomer = new Customer();
                String anonymousCpf = CPF.removeNonDigits(Constants.ANONYMOUS_CPF);
                newAnonymousCustomer.setCpf(new CPF(anonymousCpf));
                newAnonymousCustomer.setName("Anonymous");
                newAnonymousCustomer.setEmail("anonymous@fastfood.com");
                return customerGateway.insert(newAnonymousCustomer);
            }
        }
    }
}
