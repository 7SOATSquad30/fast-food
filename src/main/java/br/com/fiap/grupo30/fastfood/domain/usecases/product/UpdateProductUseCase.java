package br.com.fiap.grupo30.fastfood.domain.usecases.product;

import br.com.fiap.grupo30.fastfood.domain.entities.Category;
import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CategoryGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;

public class UpdateProductUseCase {

    public UpdateProductUseCase() {
    }

    public ProductDTO execute(
            ProductGateway productGateway, 
            CategoryGateway categoryGateway,
            Long productId,
            String name,
            String description,
            Double price,
            String imgUrl,
            String category) {
        Category categoryEntity = categoryGateway.findOne(category);

        Product product = productGateway.findById(productId);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImgUrl(imgUrl);
        product.setCategory(categoryEntity);

        return productGateway.save(product).toDTO();
    }
}
