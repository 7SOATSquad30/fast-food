package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.BiDirectionalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public final class ProductDTOMapper implements BiDirectionalMapper<Product, ProductDTO> {
    private final CategoryDTOMapper categoryDTOMapper;

    @Autowired
    public ProductDTOMapper(CategoryDTOMapper categoryDTOMapper) {
        this.categoryDTOMapper = categoryDTOMapper;
    }

    @Override
    public ProductDTO mapTo(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImgUrl(product.getImgUrl());
        dto.setCategory(categoryDTOMapper.mapTo(product.getCategory()));
        return dto;
    }

    @Override
    public Product mapFrom(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImgUrl(dto.getImgUrl());
        product.setPrice(dto.getPrice());
        product.setCategory(categoryDTOMapper.mapFrom(dto.getCategory()));
        return product;
    }
}
