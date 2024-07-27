package br.com.fiap.grupo30.fastfood.infrastructure.configuration;

import br.com.fiap.grupo30.fastfood.domain.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CategoryGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductRepository productRepository(JpaProductRepository jpaProductRepository) {
        return new ProductGateway(jpaProductRepository);
    }

    @Bean
    public ListProductsByCategoryUseCase listProductsByCategoryUseCase(
            ProductGateway productGateway) {
        return new ListProductsByCategoryUseCase(productGateway);
    }

    @Bean
    public GetProductUseCase getProductUseCase(ProductGateway productGateway) {
        return new GetProductUseCase(productGateway);
    }

    @Bean
    public CreateProductUseCase createProductUseCase(
            ProductGateway productGateway, CategoryGateway categoryGateway) {
        return new CreateProductUseCase(productGateway, categoryGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(
            ProductGateway productGateway, CategoryGateway categoryGateway) {
        return new UpdateProductUseCase(productGateway, categoryGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductGateway productGateway) {
        return new DeleteProductUseCase(productGateway);
    }
}
