package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;

public class DeleteProductUseCase {

    public void execute(ProductGateway productGateway, Long id) {
        productGateway.delete(id);
    }
}
