package br.com.fiap.grupo30.fastfood.domain.services;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();
}
