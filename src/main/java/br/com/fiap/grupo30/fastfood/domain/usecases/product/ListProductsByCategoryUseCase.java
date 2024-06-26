package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.domain.services.ProductService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ListProductsByCategoryUseCase {

    private final ProductService productService;

    public ListProductsByCategoryUseCase(ProductService productService) {
        this.productService = productService;
    }

    public List<ProductDTO> execute(Long categoryId) {
        return productService.findProductsByCategoryId(categoryId);
    }
}
