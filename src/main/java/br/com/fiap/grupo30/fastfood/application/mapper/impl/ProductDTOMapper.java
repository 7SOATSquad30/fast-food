package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.Mapper;
import br.com.fiap.grupo30.fastfood.domain.Category;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ProductDTOMapper implements Mapper<ProductDTO, ProductEntity> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductDTOMapper(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ProductEntity mapTo(ProductDTO dto) {
        ProductEntity entity = new ProductEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        CategoryEntity category = categoryRepository.getReferenceById(dto.getCategory().getId());
        entity.setCategory(category);
        return entity;
    }

    @Override
    public ProductDTO mapFrom(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setImgUrl(entity.getImgUrl());
        dto.setPrice(entity.getPrice());
        Category category = categoryMapper.mapFrom(entity.getCategory());
        dto.setCategory(new CategoryDTO(category));
        return dto;
    }

    public void updateEntityFromDTO(ProductEntity entity, ProductDTO dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());
        CategoryEntity category = categoryRepository.getReferenceById(dto.getCategory().getId());
        entity.setCategory(category);
    }
}
