package br.com.fiap.grupo30.fastfood.domain.usecases.order;

import br.com.fiap.grupo30.fastfood.domain.OrderStatus;
import br.com.fiap.grupo30.fastfood.domain.entities.Order;
import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.exceptions.CantChangeOrderProductsAfterSubmitException;

public class AddProductToOrderUseCase {

    public OrderDTO execute(
            OrderGateway orderGateway,
            ProductGateway productGateway,
            Long orderId,
            Long productId,
            Long productQuantity) {
        Order order = orderGateway.findByIdForUpdate(orderId);
        Product product = productGateway.findById(productId);

        if (order.getStatus() != OrderStatus.DRAFT) {
            throw new CantChangeOrderProductsAfterSubmitException();
        }

        order.addProduct(product, productQuantity);

        return orderGateway.save(order).toDTO();
    }
}
