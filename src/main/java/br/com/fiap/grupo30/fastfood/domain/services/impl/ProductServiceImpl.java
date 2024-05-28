package br.com.fiap.grupo30.fastfood.domain.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.ProductDTOMapper;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.ProductMapper;
import br.com.fiap.grupo30.fastfood.domain.services.ProductService;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.ProductRepository;
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

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductDTOMapper productDTOMapper;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository,
            ProductMapper productMapper,
            ProductDTOMapper productDTOMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productDTOMapper = productDTOMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findProductsByCategoryId(Long categoryId) {
        Long category = categoryId == 0 ? null : categoryId;
        return productRepository.findProductsByCategoryId(category).stream()
                .map(entity -> new ProductDTO(productMapper.mapFrom(entity)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<ProductEntity> obj = productRepository.findById(id);
        ProductEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(productMapper.mapFrom(entity));
    }

    @Override
    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        ProductEntity entity = productRepository.save(productDTOMapper.mapTo(dto));
        return new ProductDTO(productMapper.mapFrom(entity));
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            ProductEntity entity = productRepository.getReferenceById(id);
            productDTOMapper.updateEntityFromDTO(entity, dto);
            entity = productRepository.save(entity);
            return new ProductDTO(productMapper.mapFrom(entity));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id, e);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation", e);
        }
    }
}
