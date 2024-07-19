package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCategoryRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ProductDTOMapper implements Mapper<ProductDTO, ProductEntity> {

    private final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductDTOMapper(
            JpaCategoryRepository jpaCategoryRepository, CategoryMapper categoryMapper) {
        this.jpaCategoryRepository = jpaCategoryRepository;
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
        CategoryEntity category = jpaCategoryRepository.getReferenceById(dto.getCategory().getId());
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
        CategoryEntity category = jpaCategoryRepository.getReferenceById(dto.getCategory().getId());
        entity.setCategory(category);
    }
}
