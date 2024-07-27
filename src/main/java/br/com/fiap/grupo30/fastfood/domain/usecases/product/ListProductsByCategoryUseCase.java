package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListProductsByCategoryUseCase {

    private final ProductRepository productRepository;

    public ListProductsByCategoryUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> execute(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }
}
