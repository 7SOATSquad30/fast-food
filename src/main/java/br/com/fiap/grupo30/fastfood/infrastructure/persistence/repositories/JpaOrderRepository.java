package br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query(
            "SELECT obj FROM OrderEntity obj "
                    + "WHERE (:status IS NULL OR obj.status = :status) "
                    + "ORDER BY obj.createdAt ASC")
    List<OrderEntity> findOrdersByStatus(OrderStatus status);
}
