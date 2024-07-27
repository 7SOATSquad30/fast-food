package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ProductMapper implements BiDirectionalMapper<Product, ProductEntity> {

    private final CategoryEntityMapper categoryEntityMapper;

    @Autowired
    public ProductMapper(CategoryEntityMapper categoryEntityMapper) {
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public ProductEntity mapTo(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setImgUrl(product.getImgUrl());
        entity.setCategory(categoryEntityMapper.mapTo(product.getCategory()));
        return entity;
    }

    @Override
    public Product mapFrom(ProductEntity entity) {
        Product product = new Product();
        product.setId(entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());
        product.setImgUrl(entity.getImgUrl());
        product.setCategory(categoryEntityMapper.mapFrom(entity.getCategory()));
        return product;
    }
}
