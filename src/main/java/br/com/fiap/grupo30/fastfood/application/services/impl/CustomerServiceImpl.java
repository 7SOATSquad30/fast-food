package br.com.fiap.grupo30.fastfood.application.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.CustomerMapper;
import br.com.fiap.grupo30.fastfood.application.services.CustomerService;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceBadRequestException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.domain.vo.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CustomerRepository;
import jakarta.validation.ConstraintViolationException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        String cpfDTO = dto.getCpf();
        if (!CPF.isValid(cpfDTO)) {
            throw new ResourceBadRequestException("Invalid CPF: " + cpfDTO);
        }
        try {
            dto.setCpf(CPF.removeNonDigits(cpfDTO));
            CustomerEntity entity = new CustomerEntity();
            copyDtoToEntity(dto, entity);
            entity = customerRepository.save(entity);
            return new CustomerDTO(CustomerMapper.toModel(entity));
        } catch (ConstraintViolationException e) {
            throw new DatabaseException(e.getMessage(), e);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("CPF already exists: " + dto.getCpf(), e);
        }
    }

    private void copyDtoToEntity(CustomerDTO dto, CustomerEntity entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
    }
}
