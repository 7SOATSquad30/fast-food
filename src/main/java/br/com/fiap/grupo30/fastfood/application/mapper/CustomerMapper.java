package br.com.fiap.grupo30.fastfood.application.mapper;

import br.com.fiap.grupo30.fastfood.domain.Customer;
import br.com.fiap.grupo30.fastfood.domain.vo.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.getId());
        entity.setName(customer.getName());
        entity.setCpf(String.valueOf(customer.getCpf()));
        entity.setEmail(customer.getEmail());
        return entity;
    }

    public static Customer toModel(CustomerEntity entity) {
        return new Customer(
                entity.getId(), entity.getName(), new CPF(entity.getCpf()), entity.getEmail());
    }
}
