package br.com.fiap.grupo30.fastfood.application.services;

import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.domain.commands.AddProductToOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.RemoveProductFromOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.SubmitOrderCommand;

public interface OrderService {

    public OrderDTO startNewOrder();

    public OrderDTO addProductToOrder(AddProductToOrderCommand command);

    public OrderDTO removeProductFromOrder(RemoveProductFromOrderCommand command);

    public OrderDTO submitOrder(SubmitOrderCommand command);

    public OrderDTO getOrder(Long orderId);
}
