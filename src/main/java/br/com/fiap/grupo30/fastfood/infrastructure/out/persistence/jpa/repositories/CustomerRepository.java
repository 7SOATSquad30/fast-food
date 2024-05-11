package br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories;

import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(
        "SELECT obj FROM CustomerEntity obj "
            + "WHERE obj.cpf = :cpf")
    CustomerEntity findCustomerByCpf(String cpf);

}
