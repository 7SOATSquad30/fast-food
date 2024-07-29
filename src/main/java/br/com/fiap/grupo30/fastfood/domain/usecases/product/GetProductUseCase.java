package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;

public class GetProductUseCase {

    private final ProductGateway productGateway;

    public GetProductUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public ProductDTO execute(Long id) {
        return productGateway.findById(id).toDTO();
    }
}
