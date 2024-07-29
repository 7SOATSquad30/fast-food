package br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CustomerEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query("SELECT obj FROM CustomerEntity obj " + "WHERE obj.cpf = :cpf")
    Optional<CustomerEntity> findCustomerByCpf(String cpf);
}
