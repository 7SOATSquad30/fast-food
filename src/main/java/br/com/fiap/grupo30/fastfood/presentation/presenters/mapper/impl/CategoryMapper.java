package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl;

import br.com.fiap.grupo30.fastfood.domain.Category;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.CategoryEntity;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<Category, CategoryEntity> {

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
