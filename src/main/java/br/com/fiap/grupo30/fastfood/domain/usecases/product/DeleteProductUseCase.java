package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.application.services.ProductService;

public class DeleteProductUseCase {

    private final ProductService productService;

    public DeleteProductUseCase(ProductService productService) {
        this.productService = productService;
    }

    public void execute(Long id) {
        productService.delete(id);
    }
}
