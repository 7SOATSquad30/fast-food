package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.repositories.CustomerRepository;
import br.com.fiap.grupo30.fastfood.domain.valueobjects.CPF;
import br.com.fiap.grupo30.fastfood.infrastructure.configuration.Constants;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CustomerEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceConflictException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerGateway implements CustomerRepository {

    public final JpaCustomerRepository jpaCustomerRepository;

    @Autowired
    public CustomerGateway(JpaCustomerRepository jpaCustomerRepository) {
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer findCustomerByCpf(String cpf) {
        String cpfStr = CPF.removeNonDigits(cpf);
        Optional<CustomerEntity> customer = jpaCustomerRepository.findCustomerByCpf(cpfStr);

        if (Constants.ANONYMOUS_CPF.equals(cpf)) {
            return customer.map(CustomerEntity::toDomainEntity).orElse(null);
        }

        return customer.map(CustomerEntity::toDomainEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public Customer save(Customer customer) {
        try {
            CustomerEntity entity = jpaCustomerRepository.save(customer.toPersistence());
            return entity.toDomainEntity();
        } catch (DataIntegrityViolationException e) {
            throw new ResourceConflictException(
                    "CPF already exists: " + customer.getCpf().value(), e);
        }
    }
}
