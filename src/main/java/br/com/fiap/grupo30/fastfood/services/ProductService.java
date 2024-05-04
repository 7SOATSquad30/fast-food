package br.com.fiap.grupo30.fastfood.services;

import br.com.fiap.grupo30.fastfood.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.entities.Category;
import br.com.fiap.grupo30.fastfood.entities.Product;
import br.com.fiap.grupo30.fastfood.repositories.CategoryRepository;
import br.com.fiap.grupo30.fastfood.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.services.exceptions.DatabaseException;
import br.com.fiap.grupo30.fastfood.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired private ProductRepository repository;

    @Autowired private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(Long categoryId) {
        Long category = categoryId == 0 ? null : categoryId;
        return repository.findProductsByCategoryId(category).stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id, e);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + id, e);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation", e);
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        Category category = categoryRepository.getReferenceById(dto.getCategory().getId());
        entity.setCategory(category);
    }
}
