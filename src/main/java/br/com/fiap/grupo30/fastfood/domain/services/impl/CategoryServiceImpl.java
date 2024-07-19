package br.com.fiap.grupo30.fastfood.domain.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.impl.CategoryMapper;
import br.com.fiap.grupo30.fastfood.domain.services.CategoryService;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    public final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImpl(
            CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(entity -> new CategoryDTO(categoryMapper.mapFrom(entity)))
                .toList();
    }
}
