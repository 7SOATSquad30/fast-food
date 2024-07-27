package br.com.fiap.grupo30.fastfood.infrastructure.configuration;

import br.com.fiap.grupo30.fastfood.domain.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.domain.usecases.order.*;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.OrderEntityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderRepository orderRepository(
            JpaOrderRepository jpaOrderRepository, OrderEntityMapper orderEntityMapper) {
        return new OrderGateway(jpaOrderRepository, orderEntityMapper);
    }

    @Bean
    public ListOrdersUseCase listOrdersUseCase(OrderGateway orderGateway) {
        return new ListOrdersUseCase(orderGateway);
    }

    @Bean
    public GetOrderUseCase getOrderUseCase(OrderGateway orderGateway) {
        return new GetOrderUseCase(orderGateway);
    }

    @Bean
    public StartNewOrderUseCase startNewOrderUseCase(
            OrderGateway orderGateway, CustomerGateway customerGateway) {
        return new StartNewOrderUseCase(orderGateway, customerGateway);
    }

    @Bean
    public AddProductToOrderUseCase addProductToOrderUseCase(
            OrderGateway orderGateway, ProductGateway productGateway) {
        return new AddProductToOrderUseCase(orderGateway, productGateway);
    }

    @Bean
    public RemoveProductFromOrderUseCase removeProductFromOrderUseCase(
            OrderGateway orderGateway, ProductGateway productGateway) {
        return new RemoveProductFromOrderUseCase(orderGateway, productGateway);
    }

    @Bean
    public SubmitOrderUseCase submitOrderUseCase(OrderGateway orderGateway) {
        return new SubmitOrderUseCase(orderGateway);
    }

    @Bean
    public StartPreparingOrderUseCase startPreparingOrderUseCase(OrderGateway orderGateway) {
        return new StartPreparingOrderUseCase(orderGateway);
    }

    @Bean
    public FinishPreparingOrderUseCase finishPreparingOrderUseCase(OrderGateway orderGateway) {
        return new FinishPreparingOrderUseCase(orderGateway);
    }

    @Bean
    public DeliverOrderUseCase deliverOrderUseCase(OrderGateway orderGateway) {
        return new DeliverOrderUseCase(orderGateway);
    }
}
