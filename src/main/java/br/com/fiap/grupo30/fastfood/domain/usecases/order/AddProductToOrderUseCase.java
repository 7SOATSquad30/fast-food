package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderProductsAfterSubmitException;

public class AddProductToOrderUseCase {

    private final OrderGateway orderGateway;
    private final ProductGateway productGateway;

    public AddProductToOrderUseCase(OrderGateway orderGateway, ProductGateway productGateway) {
        this.orderGateway = orderGateway;
        this.productGateway = productGateway;
    }

    public Order execute(Long orderId, Long productId, Long productQuantity) {
        Order order = orderGateway.findById(orderId);

        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new CantChangeOrderProductsAfterSubmitException();
        }

        Product product = productGateway.findById(productId);
        return orderGateway.addProductToOrder(order, product, productQuantity);
    }
}
