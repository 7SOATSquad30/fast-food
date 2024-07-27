package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public final class CustomerDTOMapper implements BiDirectionalMapper<Customer, CustomerDTO> {

    @Override
    public CustomerDTO mapTo(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setCpf(customer.getCpf().value());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    @Override
    public Customer mapFrom(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setCpf(new CPF(dto.getCpf()));
        customer.setEmail(dto.getEmail());
        return customer;
    }
}
