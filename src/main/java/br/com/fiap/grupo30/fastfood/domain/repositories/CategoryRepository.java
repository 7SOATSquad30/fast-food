package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.domain.entities.Category;
import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();

    Category findOne(String category);
}
