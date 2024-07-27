package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.stereotype.Component;

@Component
public final class CustomerEntityMapper implements BiDirectionalMapper<Customer, CustomerEntity> {

    @Override
    public CustomerEntity mapTo(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setCpf(String.valueOf(customer.getCpf().value()));
        entity.setEmail(customer.getEmail());
        return entity;
    }

    @Override
    public Customer mapFrom(CustomerEntity entity) {
        Customer customer = new Customer();
        customer.setId(entity.getId());
        customer.setName(entity.getName());
        customer.setCpf(new CPF(entity.getCpf()));
        customer.setEmail(entity.getEmail());
        return customer;
    }

    public void updateEntityFromCustomer(CustomerEntity entity, Customer customer) {
        entity.setName(customer.getName());
        entity.setCpf(customer.getCpf().value());
        entity.setEmail(customer.getEmail());
    }
}