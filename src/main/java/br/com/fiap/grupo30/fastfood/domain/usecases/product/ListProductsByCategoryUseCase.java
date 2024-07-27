package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import java.util.List;

public class ListProductsByCategoryUseCase {

    private final ProductGateway productGateway;

    public ListProductsByCategoryUseCase(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    public List<Product> execute(Long categoryId) {
        return productGateway.findProductsByCategoryId(categoryId);
    }
}
