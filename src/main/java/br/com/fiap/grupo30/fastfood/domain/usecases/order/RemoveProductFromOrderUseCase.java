package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.ProductRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RemoveProductFromOrderUseCase {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public RemoveProductFromOrderUseCase(
            OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderDTO execute(Long orderId, Long productId) {
        OrderEntity order =
                this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        ProductEntity product =
                this.productRepository
                        .findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        order.removeProduct(product);
        return this.orderRepository.save(order).toDTO();
    }
}
