package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.application.mapper.BiDirectionalMapper;
import br.com.fiap.grupo30.fastfood.domain.Customer;
import br.com.fiap.grupo30.fastfood.domain.vo.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public final class CustomerMapper implements BiDirectionalMapper<Customer, CustomerEntity> {

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
}
