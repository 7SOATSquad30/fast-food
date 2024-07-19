package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddProductToOrderUseCase {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaProductRepository jpaProductRepository;

    @Autowired
    public AddProductToOrderUseCase(
            JpaOrderRepository jpaOrderRepository, JpaProductRepository jpaProductRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaProductRepository = jpaProductRepository;
    }

    @Transactional
    public OrderDTO execute(Long orderId, Long productId, Long productQuantity) {
        OrderEntity order =
                this.jpaOrderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        ProductEntity product =
                this.jpaProductRepository
                        .findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        order.addProduct(product, productQuantity);

        return this.jpaOrderRepository.save(order).toDTO();
    }
}
