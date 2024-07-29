package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;

public class DeleteProductUseCase {

    private final ProductGateway productGateway;

    public DeleteProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public void execute(Long id) {
        productGateway.delete(id);
    }
}
