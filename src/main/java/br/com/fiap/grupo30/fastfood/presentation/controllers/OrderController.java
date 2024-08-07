package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.order.*;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.AddCustomerCpfRequest;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.AddOrderProductRequest;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/orders")
@Tag(name = "Orders Controller", description = "RESTful API for managing orders.")
public class OrderController {

    private final StartNewOrderUseCase startNewOrderUseCase;
    private final AddProductToOrderUseCase addProductToOrderUseCase;
    private final RemoveProductFromOrderUseCase removeProductFromOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final SubmitOrderUseCase submitOrderUseCase;
    private final ListOrdersWithSpecificStatusesUseCase listOrdersWithSpecificStatusesUseCase;
    private final ListOrdersByStatusUseCase listAllOrdersByStatus;
    private final StartPreparingOrderUseCase startPreparingOrderUseCase;
    private final FinishPreparingOrderUseCase finishPreparingOrderUseCase;
    private final DeliverOrderUseCase deliverOrderUseCase;

    @Autowired
    public OrderController(
            StartNewOrderUseCase startNewOrderUseCase,
            AddProductToOrderUseCase addProductToOrderUseCase,
            RemoveProductFromOrderUseCase removeProductFromOrderUseCase,
            GetOrderUseCase getOrderUseCase,
            SubmitOrderUseCase submitOrderUseCase,
            ListOrdersWithSpecificStatusesUseCase listOrdersWithSpecificStatusesUseCase,
            ListOrdersByStatusUseCase listAllOrdersByStatus,
            StartPreparingOrderUseCase startPreparingOrderUseCase,
            FinishPreparingOrderUseCase finishPreparingOrderUseCase,
            DeliverOrderUseCase deliverOrderUseCase) {
        this.startNewOrderUseCase = startNewOrderUseCase;
        this.addProductToOrderUseCase = addProductToOrderUseCase;
        this.removeProductFromOrderUseCase = removeProductFromOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.submitOrderUseCase = submitOrderUseCase;
        this.listOrdersWithSpecificStatusesUseCase = listOrdersWithSpecificStatusesUseCase;
        this.listAllOrdersByStatus = listAllOrdersByStatus;
        this.startPreparingOrderUseCase = startPreparingOrderUseCase;
        this.finishPreparingOrderUseCase = finishPreparingOrderUseCase;
        this.deliverOrderUseCase = deliverOrderUseCase;
    }

    @GetMapping()
    @Operation(
            summary = "Get orders with specific status",
            description =
                    "Get a list of orders with statuses READY, PREPARING, and SUBMITTED, sorted by"
                            + " status and date")
    public ResponseEntity<List<OrderDTO>> findOrdersWithSpecificStatuses() {
        List<OrderDTO> orders = listOrdersWithSpecificStatusesUseCase.execute();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/by-status")
    @Operation(
            summary = "Get orders of any status or by status",
            description =
                    "Get a list of all registered orders of any status or by status"
                            + " sorted by date via RequestParam. i.e., ?status=")
    public ResponseEntity<List<OrderDTO>> findOrdersByStatus(
            @RequestParam(value = "status", required = false) String status) {
        List<OrderDTO> orders = listAllOrdersByStatus.execute(status);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{orderId}")
    @Operation(
            summary = "Get an order by ID",
            description = "Retrieve a specific order based on its ID")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long orderId) {
        OrderDTO order = getOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Create a new order and return the new order's data")
    public ResponseEntity<OrderDTO> startNewOrder(
            @RequestBody(required = false) AddCustomerCpfRequest request) {
        OrderDTO order = startNewOrderUseCase.execute(request.getCpf());
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
        OrderDTO order =
                addProductToOrderUseCase.execute(
                        orderId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping(value = "/{orderId}/products/{productId}")
    @Operation(
            summary = "Remove a product from an order",
            description = "Removes a product from an order")
    public ResponseEntity<OrderDTO> removeProduct(
            @PathVariable Long orderId, @PathVariable Long productId) {
        OrderDTO order = removeProductFromOrderUseCase.execute(orderId, productId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/submit")
    @Operation(
            summary = "Submit an order for preparation",
            description = "Submits an order for preparation and return the order's data")
    public ResponseEntity<OrderDTO> submitOrder(@PathVariable Long orderId) {
        OrderDTO order = submitOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/prepare")
    @Operation(
            summary = "Start preparing an order",
            description = "Start preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> startPreparingOrder(@PathVariable Long orderId) {
        OrderDTO order = startPreparingOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/ready")
    @Operation(
            summary = "Finish preparing an order",
            description = "Finish preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> finishPreparingOrder(@PathVariable Long orderId) {
        OrderDTO order = finishPreparingOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/deliver")
    @Operation(
            summary = "Deliver an order",
            description = "Deliver an order and return the order's data")
    public ResponseEntity<OrderDTO> deliverOrder(@PathVariable Long orderId) {
        OrderDTO order = deliverOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }
}
