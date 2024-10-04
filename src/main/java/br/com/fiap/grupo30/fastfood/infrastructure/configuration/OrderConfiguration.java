package br.com.fiap.grupo30.fastfood.infrastructure.configuration;

import br.com.fiap.grupo30.fastfood.domain.repositories.OrderRepository;
import br.com.fiap.grupo30.fastfood.domain.usecases.order.*;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpaOrderRepository) {
        return new OrderGateway(jpaOrderRepository);
    }

    @Bean
    public ListOrdersWithSpecificStatusesUseCase listOrdersBySpecificStatusesUseCase() {
        return new ListOrdersWithSpecificStatusesUseCase();
    }

    @Bean
    public ListOrdersByStatusUseCase listOrdersByStatusUseCase() {
        return new ListOrdersByStatusUseCase();
    }

    @Bean
    public GetOrderUseCase getOrderUseCase() {
        return new GetOrderUseCase();
    }

    @Bean
    public StartNewOrderUseCase startNewOrderUseCase() {
        return new StartNewOrderUseCase();
    }

    @Bean
    public AddProductToOrderUseCase addProductToOrderUseCase() {
        return new AddProductToOrderUseCase();
    }

    @Bean
    public RemoveProductFromOrderUseCase removeProductFromOrderUseCase() {
        return new RemoveProductFromOrderUseCase();
    }

    @Bean
    public SubmitOrderUseCase submitOrderUseCase() {
        return new SubmitOrderUseCase();
    }

    @Bean
    public StartPreparingOrderUseCase startPreparingOrderUseCase() {
        return new StartPreparingOrderUseCase();
    }

    @Bean
    public FinishPreparingOrderUseCase finishPreparingOrderUseCase() {
        return new FinishPreparingOrderUseCase();
    }

    @Bean
    public DeliverOrderUseCase deliverOrderUseCase() {
        return new DeliverOrderUseCase();
    }
}
