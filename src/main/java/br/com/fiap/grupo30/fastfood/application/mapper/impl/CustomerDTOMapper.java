package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.BiDirectionalMapper;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import org.springframework.stereotype.Component;

@Component
public final class CustomerDTOMapper implements BiDirectionalMapper<CustomerDTO, CustomerEntity> {

    @Override
    public CustomerEntity mapTo(CustomerDTO dto) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        return entity;
    }

    @Override
    public CustomerDTO mapFrom(CustomerEntity entity) {
        CustomerDTO customer = new CustomerDTO();
        customer.setId(entity.getId());
        customer.setName(entity.getName());
        customer.setCpf(entity.getCpf());
        customer.setEmail(entity.getEmail());
        return customer;
    }

    public void updateEntityFromDTO(CustomerEntity entity, CustomerDTO dto) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
    }
}
