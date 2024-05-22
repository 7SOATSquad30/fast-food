package br.com.fiap.grupo30.fastfood.application.useCases;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import java.util.List;

public interface CategoryUseCase {

    List<CategoryDTO> findProducts();
}
