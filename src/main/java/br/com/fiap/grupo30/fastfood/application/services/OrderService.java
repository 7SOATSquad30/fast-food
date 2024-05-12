package br.com.fiap.grupo30.fastfood.application.services;

import br.com.fiap.grupo30.fastfood.domain.commands.AddProductToOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.SubmitOrderCommand;
import br.com.fiap.grupo30.fastfood.infrastructure.out.persistence.jpa.entities.OrderEntity;

public interface OrderService {

    public OrderEntity startNewOrder();

    public void addProductToOrder(AddProductToOrderCommand addProductCommand);

    public void submitOrder(SubmitOrderCommand command);

}
