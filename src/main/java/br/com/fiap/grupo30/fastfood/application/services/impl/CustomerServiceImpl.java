package br.com.fiap.grupo30.fastfood.application.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.CustomerMapper;
import br.com.fiap.grupo30.fastfood.application.services.CustomerService;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    public CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerDTO findCustomerByCpf(String cpf) {
        Optional<CustomerEntity> obj = customerRepository.findCustomerByCpf(cpf);
        CustomerEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CustomerDTO(CustomerMapper.toModel(entity));
    }

    @Override
    public CustomerDTO insert(CustomerDTO dto) {
        return null;
    }
}
