package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.services.CategoryService;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CategoryDTO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListAllCategoriesInMenuUseCase {

    private final CategoryService categoryService;

    public ListAllCategoriesInMenuUseCase(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public List<CategoryDTO> execute() {
        return categoryService.findAll();
    }
}
