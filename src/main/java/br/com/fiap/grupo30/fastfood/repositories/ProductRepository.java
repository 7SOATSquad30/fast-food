package br.com.fiap.grupo30.fastfood.repositories;

import br.com.fiap.grupo30.fastfood.entities.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(
            "SELECT obj FROM Product obj "
                    + "WHERE (:category IS NULL OR obj.category.id = :category)")
    List<Product> findProductsByCategoryId(Long category);
}
