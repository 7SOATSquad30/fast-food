package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;

public interface CustomerRepository {
    Customer findCustomerByCpf(String cpf);

    Customer save(Customer dto);
}
