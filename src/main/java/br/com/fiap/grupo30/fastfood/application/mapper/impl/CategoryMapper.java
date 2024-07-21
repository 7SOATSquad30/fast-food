package br.com.fiap.grupo30.fastfood.application.mapper.impl;

import br.com.fiap.grupo30.fastfood.application.mapper.BiDirectionalMapper;
import br.com.fiap.grupo30.fastfood.domain.Category;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements BiDirectionalMapper<Category, CategoryEntity> {

    @Override
    public CategoryEntity mapTo(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        return entity;
    }

    @Override
    public Category mapFrom(CategoryEntity entity) {
        Category category = new Category();
        category.setId(entity.getId());
        category.setName(entity.getName());
        return category;
    }
}
