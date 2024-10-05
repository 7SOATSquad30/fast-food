package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.order.*;
import br.com.fiap.grupo30.fastfood.infrastructure.auth.AdminRequired;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CustomerGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.OrderGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.ProductGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCustomerRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaOrderRepository;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaProductRepository;
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
    private final JpaOrderRepository jpaOrderRepository;
    private final JpaProductRepository jpaProductRepository;
    private final JpaCustomerRepository jpaCustomerRepository;

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
            DeliverOrderUseCase deliverOrderUseCase,
            JpaOrderRepository jpaOrderRepository,
            JpaProductRepository jpaProductRepository,
            JpaCustomerRepository jpaCustomerRepository) {
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
        this.jpaOrderRepository = jpaOrderRepository;
        this.jpaProductRepository = jpaProductRepository;
        this.jpaCustomerRepository = jpaCustomerRepository;
    }

    @GetMapping()
    @Operation(
            summary = "Get orders with specific status",
            description =
                    "Get a list of orders with statuses READY, PREPARING, and SUBMITTED, sorted by"
                            + " status and date")
    public ResponseEntity<List<OrderDTO>> findOrdersWithSpecificStatuses() {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        List<OrderDTO> orders = listOrdersWithSpecificStatusesUseCase.execute(orderGateway);
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
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        List<OrderDTO> orders = listAllOrdersByStatus.execute(orderGateway, status);
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{orderId}")
    @Operation(
            summary = "Get an order by ID",
            description = "Retrieve a specific order based on its ID")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long orderId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        OrderDTO order = getOrderUseCase.execute(orderGateway, orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Create a new order and return the new order's data")
    public ResponseEntity<OrderDTO> startNewOrder(
            @RequestBody(required = false) AddCustomerCpfRequest request) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        CustomerGateway customerGateway = new CustomerGateway(jpaCustomerRepository);
        OrderDTO order =
                startNewOrderUseCase.execute(orderGateway, customerGateway, request.getCpf());
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
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        ProductGateway productGateway = new ProductGateway(jpaProductRepository);
        OrderDTO order =
                addProductToOrderUseCase.execute(
                        orderGateway,
                        productGateway,
                        orderId,
                        request.getProductId(),
                        request.getQuantity());
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping(value = "/{orderId}/products/{productId}")
    @Operation(
            summary = "Remove a product from an order",
            description = "Removes a product from an order")
    public ResponseEntity<OrderDTO> removeProduct(
            @PathVariable Long orderId, @PathVariable Long productId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        ProductGateway productGateway = new ProductGateway(jpaProductRepository);
        OrderDTO order =
                removeProductFromOrderUseCase.execute(
                        orderGateway, productGateway, orderId, productId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/submit")
    @Operation(
            summary = "Submit an order for preparation",
            description = "Submits an order for preparation and return the order's data")
    public ResponseEntity<OrderDTO> submitOrder(@PathVariable Long orderId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        OrderDTO order = submitOrderUseCase.execute(orderGateway, orderId);
        return ResponseEntity.ok().body(order);
    }

    @AdminRequired()
    @PostMapping(value = "/{orderId}/prepare")
    @Operation(
            summary = "Start preparing an order",
            description = "Start preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> startPreparingOrder(@PathVariable Long orderId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        OrderDTO order = startPreparingOrderUseCase.execute(orderGateway, orderId);
        return ResponseEntity.ok().body(order);
    }

    @AdminRequired()
    @PostMapping(value = "/{orderId}/ready")
    @Operation(
            summary = "Finish preparing an order",
            description = "Finish preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> finishPreparingOrder(@PathVariable Long orderId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        OrderDTO order = finishPreparingOrderUseCase.execute(orderGateway, orderId);
        return ResponseEntity.ok().body(order);
    }

    @AdminRequired()
    @PostMapping(value = "/{orderId}/deliver")
    @Operation(
            summary = "Deliver an order",
            description = "Deliver an order and return the order's data")
    public ResponseEntity<OrderDTO> deliverOrder(@PathVariable Long orderId) {
        OrderGateway orderGateway = new OrderGateway(jpaOrderRepository);
        OrderDTO order = deliverOrderUseCase.execute(orderGateway, orderId);
        return ResponseEntity.ok().body(order);
    }
}
