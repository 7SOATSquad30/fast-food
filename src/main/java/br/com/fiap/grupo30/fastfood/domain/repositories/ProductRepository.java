package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import java.util.List;

public interface ProductRepository {

    List<Product> findProductsByCategoryId(Long categoryId);

    Product findById(Long id);

    Product insert(Product product);

    Product update(Long id, Product product);

    void delete(Long id);
}
