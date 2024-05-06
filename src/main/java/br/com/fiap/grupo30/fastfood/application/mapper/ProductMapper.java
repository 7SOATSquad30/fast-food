package br.com.fiap.grupo30.fastfood.application.mapper;

import br.com.fiap.grupo30.fastfood.domain.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;

public final class ProductMapper {

    private ProductMapper() {}

    public static ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());
        entity.setImgUrl(product.getImgUrl());
        entity.setCategory(CategoryMapper.toEntity(product.getCategory()));
        return entity;
    }

    public static Product toModel(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getImgUrl(),
                CategoryMapper.toModel(entity.getCategory()));
    }
}
