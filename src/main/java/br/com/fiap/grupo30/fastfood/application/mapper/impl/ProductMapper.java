package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.application.mapper.Mapper;
import br.com.fiap.grupo30.fastfood.domain.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ProductMapper implements Mapper<Product, ProductEntity> {

    private final CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ProductEntity mapTo(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setImgUrl(product.getImgUrl());
        entity.setCategory(categoryMapper.mapTo(product.getCategory()));
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
        product.setCategory(categoryMapper.mapFrom(entity.getCategory()));
        return product;
    }
}
