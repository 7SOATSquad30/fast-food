package br.com.fiap.grupo30.fastfood.application.useCases.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.application.services.CategoryService;
import br.com.fiap.grupo30.fastfood.application.useCases.CategoryUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryUseCaseImpl implements CategoryUseCase {

    private final CategoryService categoryService;

    public CategoryUseCaseImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public List<CategoryDTO> findProducts() {
        return categoryService.findAll();
    }
}
