package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.services.ProductService;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class CreateProductUseCase {

    private final ProductService productService;

    public CreateProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public ProductDTO execute(ProductDTO product) {
        return productService.insert(product);
    }
}
