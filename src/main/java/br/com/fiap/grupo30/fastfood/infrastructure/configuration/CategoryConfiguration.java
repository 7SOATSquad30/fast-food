package br.com.fiap.grupo30.fastfood.infrastructure.configuration;

import br.com.fiap.grupo30.fastfood.domain.repositories.CategoryRepository;
import br.com.fiap.grupo30.fastfood.domain.usecases.category.ListAllCategoriesInMenuUseCase;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CategoryGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCategoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryConfiguration {

    @Bean
    public CategoryRepository categoryRepository(JpaCategoryRepository jpaCategoryRepository) {
        return new CategoryGateway(jpaCategoryRepository);
    }

    @Bean
    public ListAllCategoriesInMenuUseCase listAllCategoriesInMenuUseCase() {
        return new ListAllCategoriesInMenuUseCase();
    }
}
