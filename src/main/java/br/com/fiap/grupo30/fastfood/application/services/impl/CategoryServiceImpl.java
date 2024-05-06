package br.com.fiap.grupo30.fastfood.application.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.application.mapper.CategoryMapper;
import br.com.fiap.grupo30.fastfood.application.services.CategoryService;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.CategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    public CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(entity -> new CategoryDTO(CategoryMapper.toModel(entity)))
                .toList();
    }
}
