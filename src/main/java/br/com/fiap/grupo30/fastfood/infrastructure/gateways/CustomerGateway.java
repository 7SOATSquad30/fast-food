package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.repositories.CustomerRepository;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.configuration.Constants;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.CustomerEntityMapper;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerGateway implements CustomerRepository {

    public final JpaCustomerRepository jpaCustomerRepository;
    public final CustomerEntityMapper customerEntityMapper;

    @Autowired
    public CustomerGateway(
            JpaCustomerRepository jpaCustomerRepository,
            CustomerEntityMapper customerEntityMapper) {
        this.jpaCustomerRepository = jpaCustomerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findCustomerByCpf(String cpf) {
        String cpfStr = CPF.removeNonDigits(cpf);
        Optional<CustomerEntity> obj;
        CustomerEntity entity;
        if (Constants.ANONYMOUS_CPF.equals(cpf)) {
            obj = jpaCustomerRepository.findCustomerByCpf(cpfStr);
            return obj.map(this.customerEntityMapper::mapFrom).orElse(null);
        }
        obj = jpaCustomerRepository.findCustomerByCpf(cpfStr);
        entity = obj.orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return this.customerEntityMapper.mapFrom(entity);
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            CustomerEntity entity =
                    jpaCustomerRepository.save(customerEntityMapper.mapTo(customer));
            return this.customerEntityMapper.mapFrom(entity);
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException(
                    "CPF already exists: " + customer.getCpf().value(), e);
        }
    }
}
