package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.repositories.CategoryRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CategoryDTO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListAllCategoriesInMenuUseCase {

    private final CategoryRepository categoryRepository;

    public ListAllCategoriesInMenuUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> execute() {
        return categoryRepository.findAll();
    }
}
