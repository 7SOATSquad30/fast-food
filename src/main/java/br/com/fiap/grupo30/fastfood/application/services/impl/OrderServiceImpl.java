package br.com.fiap.grupo30.fastfood.application.services.impl;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.services.OrderService;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.ResourceNotFoundException;
import br.com.fiap.grupo30.fastfood.application.services.exceptions.UserCantChangeOrderAfterSubmitException;
import br.com.fiap.grupo30.fastfood.domain.commands.AddProductToOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.RemoveProductFromOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.SubmitOrderCommand;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderStatus;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.ProductEntity;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderDTO startNewOrder() {
        OrderEntity newOrder = OrderEntity.create();
        newOrder = this.orderRepository.save(newOrder);
        return newOrder.toDTO();
    }

    @Override
    @Transactional
    public OrderDTO addProductToOrder(AddProductToOrderCommand command) {
        OrderEntity order =
                this.orderRepository
                        .findById(command.getOrderId())
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.isDraft()) {
            throw new UserCantChangeOrderAfterSubmitException(
                    "Can only add products to an order in draft");
        }

        ProductEntity product =
                this.productRepository
                        .findById(command.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        order.addProduct(product, command.getProductQuantity());
        return this.orderRepository.save(order).toDTO();
    }

    @Override
    @Transactional
    public OrderDTO removeProductFromOrder(RemoveProductFromOrderCommand command) {
        OrderEntity order =
                this.orderRepository
                        .findById(command.getOrderId())
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.isDraft()) {
            throw new UserCantChangeOrderAfterSubmitException(
                    "Can only remove products from an order in draft");
        }

        ProductEntity product =
                this.productRepository
                        .findById(command.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        order.removeProduct(product);
        return this.orderRepository.save(order).toDTO();
    }

    @Override
    @Transactional
    public OrderDTO submitOrder(SubmitOrderCommand command) {
        OrderEntity order =
                this.orderRepository
                        .findById(command.getOrderId())
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(OrderStatus.SUBMITTED);
        return this.orderRepository.save(order).toDTO();
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        OrderEntity order =
                this.orderRepository
                        .findById(orderId)
                        .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return order.toDTO();
    }
}
