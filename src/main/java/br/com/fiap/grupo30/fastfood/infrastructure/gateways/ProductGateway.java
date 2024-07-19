package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.domain.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.ProductEntityMapper;
import jakarta.persistence.EntityNotFoundException;
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
    private final ProductEntityMapper productEntityMapper;

    @Autowired
    public ProductGateway(
            JpaProductRepository jpaProductRepository, ProductEntityMapper productEntityMapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProductsByCategoryId(Long categoryId) {
        Long category = categoryId == 0 ? null : categoryId;
        return jpaProductRepository.findProductsByCategoryId(category).stream()
                .map(this.productEntityMapper::mapFrom)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findById(Long id) {
        Optional<ProductEntity> obj = jpaProductRepository.findById(id);
        ProductEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return this.productEntityMapper.mapFrom(entity);
    }

    @Override
    @Transactional
    public Product insert(Product product) {
        ProductEntity entity = jpaProductRepository.save(productEntityMapper.mapTo(product));
        return this.productEntityMapper.mapFrom(entity);
    }

    @Override
    @Transactional
    public Product update(Long id, Product product) {
        try {
            ProductEntity entity = jpaProductRepository.getReferenceById(id);
            productEntityMapper.updateEntityFromProduct(entity, product);
            entity = jpaProductRepository.save(entity);
            return this.productEntityMapper.mapFrom(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id, e);
        }
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
