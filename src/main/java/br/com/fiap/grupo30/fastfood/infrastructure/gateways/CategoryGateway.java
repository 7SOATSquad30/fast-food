package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.repositories.CategoryRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCategoryRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.CategoryMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryGateway implements CategoryRepository {

    public final JpaCategoryRepository jpaCategoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryGateway(
            JpaCategoryRepository jpaCategoryRepository, CategoryMapper categoryMapper) {
        this.jpaCategoryRepository = jpaCategoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return jpaCategoryRepository.findAll().stream()
                .map(entity -> new CategoryDTO(categoryMapper.mapFrom(entity)))
                .toList();
    }
}
