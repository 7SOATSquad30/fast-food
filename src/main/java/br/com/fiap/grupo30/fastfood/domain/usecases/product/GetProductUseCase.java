package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.services.ProductService;

public class GetProductUseCase {

    private final ProductService productService;

    public GetProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public ProductDTO findProductById(Long id) {
        return productService.findById(id);
    }
}
