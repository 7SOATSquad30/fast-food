package br.com.fiap.grupo30.fastfood.infrastructure.gateways;

import br.com.fiap.grupo30.fastfood.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.domain.repositories.CategoryRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCategoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryGateway implements CategoryRepository {

    public final JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    public CategoryGateway(JpaCategoryRepository jpaCategoryRepository) {
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return jpaCategoryRepository.findAll().stream()
                .map(entity -> new Category(entity.getId(), entity.getName()))
                .toList();
    }
}
