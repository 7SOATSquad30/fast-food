package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;

public class GetProductUseCase {

    public ProductDTO execute(ProductGateway productGateway, Long id) {
        return productGateway.findById(id).toDTO();
    }
}
