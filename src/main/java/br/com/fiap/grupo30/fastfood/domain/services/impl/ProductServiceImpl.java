package br.com.fiap.grupo30.fastfood.domain.services.impl;

import br.com.fiap.grupo30.fastfood.domain.services.ProductService;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.ProductDTOMapper;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final JpaProductRepository jpaProductRepository;
    private final ProductMapper productMapper;
    private final ProductDTOMapper productDTOMapper;

    @Autowired
    public ProductServiceImpl(
            JpaProductRepository jpaProductRepository,
            ProductMapper productMapper,
            ProductDTOMapper productDTOMapper) {
        this.jpaProductRepository = jpaProductRepository;
        this.productMapper = productMapper;
        this.productDTOMapper = productDTOMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findProductsByCategoryId(Long categoryId) {
        Long category = categoryId == 0 ? null : categoryId;
        return jpaProductRepository.findProductsByCategoryId(category).stream()
                .map(entity -> new ProductDTO(productMapper.mapFrom(entity)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<ProductEntity> obj = jpaProductRepository.findById(id);
        ProductEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(productMapper.mapFrom(entity));
    }

    @Override
    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        ProductEntity entity = jpaProductRepository.save(productDTOMapper.mapTo(dto));
        return new ProductDTO(productMapper.mapFrom(entity));
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            ProductEntity entity = jpaProductRepository.getReferenceById(id);
            productDTOMapper.updateEntityFromDTO(entity, dto);
            entity = jpaProductRepository.save(entity);
            return new ProductDTO(productMapper.mapFrom(entity));
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
