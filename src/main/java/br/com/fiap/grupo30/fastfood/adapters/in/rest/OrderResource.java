package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.AddOrderProductRequest;
import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.application.services.OrderService;
import br.com.fiap.grupo30.fastfood.domain.commands.AddProductToOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.RemoveProductFromOrderCommand;
import br.com.fiap.grupo30.fastfood.domain.commands.SubmitOrderCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/orders")
@Tag(name = "Orders Resource", description = "RESTful API for managing orders.")
public class OrderResource {

    private final OrderService orderService;

    @Autowired
    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{orderId}")
    @Operation(
            summary = "Get an order by ID",
            description = "Retrieve a specific order based on its ID")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long orderId) {
        OrderDTO order = orderService.getOrder(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Create a new order and return the new order's data")
    public ResponseEntity<OrderDTO> startNewOrder() {
        OrderDTO order = orderService.startNewOrder();
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{orderId}")
                        .buildAndExpand(order.getOrderId())
                        .toUri();
        return ResponseEntity.created(uri).body(order);
    }

    @PostMapping(value = "/{orderId}/products")
    @Operation(summary = "Add a product to an order", description = "Adds a product to an order")
    public ResponseEntity<OrderDTO> addProduct(
            @PathVariable Long orderId, @RequestBody AddOrderProductRequest request) {
        var command = new AddProductToOrderCommand();
        command.setOrderId(orderId);
        command.setProductId(request.getProductId());
        command.setProductQuantity(request.getQuantity());

        OrderDTO order = orderService.addProductToOrder(command);
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping(value = "/{orderId}/products/{productId}")
    @Operation(
            summary = "Remove a product from an order",
            description = "Removes a product from an order")
    public ResponseEntity<OrderDTO> removeProduct(
            @PathVariable Long orderId, @PathVariable Long productId) {
        var command = new RemoveProductFromOrderCommand();
        command.setOrderId(orderId);
        command.setProductId(productId);

        OrderDTO order = orderService.removeProductFromOrder(command);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/submit")
    @Operation(
            summary = "Submit an order for preparation",
            description = "Submits an order for preparation and return the order's data")
    public ResponseEntity<OrderDTO> submitOrder(@PathVariable Long orderId) {
        var command = new SubmitOrderCommand();
        command.setOrderId(orderId);

        OrderDTO order = orderService.submitOrder(command);
        return ResponseEntity.ok().body(order);
    }
}
