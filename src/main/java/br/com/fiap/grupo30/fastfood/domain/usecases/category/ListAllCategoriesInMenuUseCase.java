package br.com.fiap.grupo30.fastfood.domain.usecases.category;

import br.com.fiap.grupo30.fastfood.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CategoryGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CategoryDTO;
import java.util.List;

public class ListAllCategoriesInMenuUseCase {

    private final CategoryGateway categoryGateway;

    public ListAllCategoriesInMenuUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    public List<CategoryDTO> execute() {
        return categoryGateway.findAll().stream().map(Category::toDTO).toList();
    }
}
