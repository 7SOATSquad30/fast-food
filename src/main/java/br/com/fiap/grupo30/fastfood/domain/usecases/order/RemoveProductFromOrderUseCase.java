package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.ProductRepository;
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
