package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.CustomerMapper;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CustomerRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartNewOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    @Autowired
    public StartNewOrderUseCase(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            CustomerMapper customerMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional
    public OrderDTO execute(CustomerDTO dto) {
        CustomerDTO customerDTO = dto;
        if (customerDTO == null) {
            customerDTO = findOrCreateAnonymousCustomer();
        }

        CustomerEntity customerEntity =
                customerRepository
                        .findById(customerDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        OrderEntity newOrder = OrderEntity.create();
        newOrder.setCustomer(customerEntity);

        newOrder = this.orderRepository.save(newOrder);
        return newOrder.toDTO();
    }

    private CustomerDTO findOrCreateAnonymousCustomer() {
        String anonymousCpf = "970.410.008-69";
        Optional<CustomerEntity> anonymousCustomer =
                customerRepository.findCustomerByCpf(anonymousCpf);

        if (anonymousCustomer.isPresent()) {
            return new CustomerDTO(customerMapper.mapFrom(anonymousCustomer.get()));
        } else {
            CustomerEntity newAnonymousCustomer = new CustomerEntity();
            newAnonymousCustomer.setCpf(anonymousCpf);
            newAnonymousCustomer.setName("Anonymous");
            newAnonymousCustomer.setEmail("anonymous@fastfood.com");
            CustomerEntity savedAnonymousCustomer = customerRepository.save(newAnonymousCustomer);
            return new CustomerDTO(customerMapper.mapFrom(savedAnonymousCustomer));
        }
    }
}
