package br.com.fiap.grupo30.fastfood.application.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.grupo30.fastfood.application.services.OrderService;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.CantAddProductToOrderException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.domain.commands.AddProductToOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.SubmitOrderCommand;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(
            OrderRepository orderRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderEntity startNewOrder() {
        OrderEntity newOrder = OrderEntity.create();
        return this.orderRepository.save(newOrder);
    }

    @Override
    @Transactional
    public void addProductToOrder(AddProductToOrderCommand command) {
        OrderEntity order =
            this.orderRepository.findById(command.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.isDraft()) {
            throw new CantAddProductToOrderException("Can only add products to an order in draft");
        }

        ProductEntity product = this.productRepository.findById(command.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        order.addProduct(product, command.getProductQuantity());
        this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void submitOrder(SubmitOrderCommand command) {
        OrderEntity order =
            this.orderRepository.findById(command.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(OrderStatus.SUBMITTED);
        this.orderRepository.save(order);
    }
    
}
