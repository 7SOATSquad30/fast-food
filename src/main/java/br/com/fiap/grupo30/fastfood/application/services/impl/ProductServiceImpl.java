package br.com.fiap.grupo30.fastfood.application.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.ProductMapper;
import br.com.fiap.grupo30.fastfood.application.services.ProductService;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(
            ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> findProductsByCategoryId(Long categoryId) {
        Long category = categoryId == 0 ? null : categoryId;
        return productRepository.findProductsByCategoryId(category).stream()
                .map(entity -> new ProductDTO(ProductMapper.toModel(entity)))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<ProductEntity> obj = productRepository.findById(id);
        ProductEntity entity =
                obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(ProductMapper.toModel(entity));
    }

    @Override
    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(ProductMapper.toModel(entity));
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            ProductEntity entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(ProductMapper.toModel(entity));
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

    private void copyDtoToEntity(ProductDTO dto, ProductEntity entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        CategoryEntity category = categoryRepository.getReferenceById(dto.getCategory().getId());
        entity.setCategory(category);
    }
}
