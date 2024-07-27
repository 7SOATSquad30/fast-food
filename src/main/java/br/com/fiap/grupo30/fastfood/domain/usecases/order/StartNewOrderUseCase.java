package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.CustomerEntityMapper;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartNewOrderUseCase {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaCustomerRepository jpaCustomerRepository;
    public final CustomerEntityMapper customerEntityMapper;

    @Autowired
    public StartNewOrderUseCase(
            JpaOrderRepository jpaOrderRepository,
            JpaCustomerRepository jpaCustomerRepository,
            CustomerEntityMapper customerEntityMapper) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    @Transactional
    public OrderDTO execute(CustomerDTO dto) {
        CustomerDTO customerDTO = dto;
        if (customerDTO == null) {
            customerDTO = findOrCreateAnonymousCustomer();
        }

        CustomerEntity customerEntity =
                jpaCustomerRepository
                        .findById(customerDTO.getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        OrderEntity newOrder = OrderEntity.create();
        newOrder.setCustomer(customerEntity);

        newOrder = this.jpaOrderRepository.save(newOrder);
        return newOrder.toDTO();
    }

    private CustomerDTO findOrCreateAnonymousCustomer() {
        String anonymousCpf = "970.410.008-69";
        Optional<CustomerEntity> anonymousCustomer =
                jpaCustomerRepository.findCustomerByCpf(anonymousCpf);

        if (anonymousCustomer.isPresent()) {
            return new CustomerDTO(customerEntityMapper.mapFrom(anonymousCustomer.get()));
        } else {
            CustomerEntity newAnonymousCustomer = new CustomerEntity();
            newAnonymousCustomer.setCpf(anonymousCpf);
            newAnonymousCustomer.setName("Anonymous");
            newAnonymousCustomer.setEmail("anonymous@fastfood.com");
            CustomerEntity savedAnonymousCustomer =
                    jpaCustomerRepository.save(newAnonymousCustomer);
            return new CustomerDTO(customerEntityMapper.mapFrom(savedAnonymousCustomer));
        }
    }
}
