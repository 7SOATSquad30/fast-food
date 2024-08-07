package br.com.fiap.grupo30.fastfood.infrastructure.configuration;

import br.com.fiap.grupo30.fastfood.domain.repositories.CustomerRepository;
import br.com.fiap.grupo30.fastfood.domain.usecases.customer.FindCustomerByCpfUseCase;
import br.com.fiap.grupo30.fastfood.domain.usecases.customer.RegisterNewCustomerUseCase;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCustomerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerConfiguration {

    @Bean
    public CustomerRepository customerRepository(JpaCustomerRepository jpaCustomerRepository) {
        return new CustomerGateway(jpaCustomerRepository);
    }

    @Bean
    public FindCustomerByCpfUseCase findCustomerByCpfUseCase(CustomerGateway customerGateway) {
        return new FindCustomerByCpfUseCase(customerGateway);
    }

    @Bean
    public RegisterNewCustomerUseCase registerNewCustomerUseCase(CustomerGateway customerGateway) {
        return new RegisterNewCustomerUseCase(customerGateway);
    }
}
