package br.com.fiap.grupo30.fastfood.application.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.CustomerDTOMapper;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.CustomerMapper;
import br.com.fiap.grupo30.fastfood.application.services.CustomerService;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceBadRequestException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.domain.vo.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CustomerRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    public final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;
    public final CustomerDTOMapper customerDTOMapper;

    @Autowired
    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper,
            CustomerDTOMapper customerDTOMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.customerDTOMapper = customerDTOMapper;
    }

    @Override
    public CustomerDTO findCustomerByCpf(String cpf) {
        String cpfStr = CPF.removeNonDigits(cpf);
        Optional<CustomerEntity> obj = customerRepository.findCustomerByCpf(cpfStr);
        CustomerEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new CustomerDTO(customerMapper.mapFrom(entity));
    }

    @Override
    public CustomerDTO insert(CustomerDTO dto) {
        String cpfDTO = dto.getCpf();
        if (!CPF.isValid(cpfDTO)) {
            throw new ResourceBadRequestException("Invalid CPF: " + cpfDTO);
        }
        try {
            dto.setCpf(CPF.removeNonDigits(cpfDTO));
            CustomerEntity entity = customerRepository.save(customerDTOMapper.mapTo(dto));
            return new CustomerDTO(customerMapper.mapFrom(entity));
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException("CPF already exists: " + dto.getCpf(), e);
        }
    }
}
