package br.com.fiap.grupo30.fastfood.application.mapper;

import br.com.fiap.grupo30.fastfood.domain.Category;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.CategoryEntity;

public final class CategoryMapper {

    private CategoryMapper() {}

    public static CategoryEntity toEntity(Category category) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        return entity;
    }

    public static Category toModel(CategoryEntity entity) {
        return new Category(entity.getId(), entity.getName());
    }
}
