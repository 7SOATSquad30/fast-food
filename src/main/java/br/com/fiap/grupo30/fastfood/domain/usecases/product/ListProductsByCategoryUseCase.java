package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import java.util.List;

public class ListProductsByCategoryUseCase {

    public List<ProductDTO> execute(ProductGateway productGateway, Long categoryId) {
        return productGateway.findProductsByCategoryId(categoryId).stream()
                .map(Product::toDTO)
                .toList();
    }
}
