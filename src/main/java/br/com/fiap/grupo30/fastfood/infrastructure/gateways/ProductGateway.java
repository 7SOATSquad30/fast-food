package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.domain.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductGateway implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    @Autowired
    public ProductGateway(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProductsByCategoryId(Long categoryId) {
        Long category = categoryId == 0 ? null : categoryId;
        return jpaProductRepository.findProductsByCategoryId(category).stream()
                .map(ProductEntity::toDomainEntity)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Optional<ProductEntity> obj = jpaProductRepository.findById(id);
        ProductEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return entity.toDomainEntity();
    }

    @Override
    @Transactional
    public Product save(Product product) {
        ProductEntity entity = jpaProductRepository.save(product.toPersistence());
        return entity.toDomainEntity();
    }

    @Override
    public void delete(Long id) {
        try {
            jpaProductRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id, e);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation", e);
        }
    }
}
