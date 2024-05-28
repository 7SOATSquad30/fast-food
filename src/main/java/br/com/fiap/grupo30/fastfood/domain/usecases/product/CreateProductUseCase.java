package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.services.ProductService;
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
