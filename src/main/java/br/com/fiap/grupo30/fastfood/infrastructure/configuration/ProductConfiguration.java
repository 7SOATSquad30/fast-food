package br.com.fiap.grupo30.fastfood.infrastructure.configuration;

import br.com.fiap.grupo30.fastfood.domain.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.ProductEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductConfiguration {

    @Bean
    public ProductRepository productRepository(
            JpaProductRepository jpaProductRepository, ProductEntityMapper productEntityMapper) {
        return new ProductGateway(jpaProductRepository, productEntityMapper);
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
    public CreateProductUseCase createProductUseCase(ProductGateway productGateway) {
        return new CreateProductUseCase(productGateway);
    }

    @Bean
    public UpdateProductUseCase updateProductUseCase(ProductGateway productGateway) {
        return new UpdateProductUseCase(productGateway);
    }

    @Bean
    public DeleteProductUseCase deleteProductUseCase(ProductGateway productGateway) {
        return new DeleteProductUseCase(productGateway);
    }
}
