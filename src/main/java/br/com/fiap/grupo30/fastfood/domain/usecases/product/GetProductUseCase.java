package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.services.ProductService;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class GetProductUseCase {

    private final ProductService productService;

    public GetProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public ProductDTO execute(Long id) {
        return productService.findById(id);
    }
}
