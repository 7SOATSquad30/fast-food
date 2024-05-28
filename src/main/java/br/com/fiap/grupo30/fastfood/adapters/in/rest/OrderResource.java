package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.AddCustomerCpfRequest;
import br.com.fiap.grupo30.fastfood.application.dto.AddOrderProductRequest;
import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.domain.usecases.customer.FindCustomerByCpfUseCase;
import br.com.fiap.grupo30.fastfood.domain.usecases.order.*;
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

    private final StartNewOrderUseCase startNewOrderUseCase;
    private final AddProductToOrderUseCase addProductToOrderUseCase;
    private final RemoveProductFromOrderUseCase removeProductFromOrderUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final SubmitOrderUseCase submitOrderUseCase;
    private final ListOrdersUseCase listAllOrdersUseCase;
    private final StartPreparingOrderUseCase startPreparingOrderUseCase;
    private final FinishPreparingOrderUseCase finishPreparingOrderUseCase;
    private final DeliverOrderUseCase deliverOrderUseCase;
    private final FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @Autowired
    public OrderResource(
            StartNewOrderUseCase startNewOrderUseCase,
            AddProductToOrderUseCase addProductToOrderUseCase,
            RemoveProductFromOrderUseCase removeProductFromOrderUseCase,
            GetOrderUseCase getOrderUseCase,
            SubmitOrderUseCase submitOrderUseCase,
            ListOrdersUseCase listAllOrdersUseCase,
            StartPreparingOrderUseCase startPreparingOrderUseCase,
            FinishPreparingOrderUseCase finishPreparingOrderUseCase,
            DeliverOrderUseCase deliverOrderUseCase,
            FindCustomerByCpfUseCase findCustomerByCpfUseCase) {
        this.startNewOrderUseCase = startNewOrderUseCase;
        this.addProductToOrderUseCase = addProductToOrderUseCase;
        this.removeProductFromOrderUseCase = removeProductFromOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.submitOrderUseCase = submitOrderUseCase;
        this.listAllOrdersUseCase = listAllOrdersUseCase;
        this.startPreparingOrderUseCase = startPreparingOrderUseCase;
        this.finishPreparingOrderUseCase = finishPreparingOrderUseCase;
        this.deliverOrderUseCase = deliverOrderUseCase;
        this.findCustomerByCpfUseCase = findCustomerByCpfUseCase;
    }

    @GetMapping(value = "/")
    @Operation(summary = "List all orders", description = "Return all orders")
    public ResponseEntity<OrderDTO[]> listAllOrders() {
        OrderDTO[] orders = this.listAllOrdersUseCase.execute();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping(value = "/{orderId}")
    @Operation(
            summary = "Get an order by ID",
            description = "Retrieve a specific order based on its ID")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long orderId) {
        OrderDTO order = this.getOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Create a new order and return the new order's data")
    public ResponseEntity<OrderDTO> startNewOrder(
            @RequestBody(required = false) AddCustomerCpfRequest request) {
        CustomerDTO customerDTO = null;
        if (request != null && request.getCpf() != null && !request.getCpf().isEmpty()) {
            customerDTO = findCustomerByCpfUseCase.execute(request.getCpf());
        }
        OrderDTO order = this.startNewOrderUseCase.execute(customerDTO);
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
                this.addProductToOrderUseCase.execute(
                        orderId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping(value = "/{orderId}/products/{productId}")
    @Operation(
            summary = "Remove a product from an order",
            description = "Removes a product from an order")
    public ResponseEntity<OrderDTO> removeProduct(
            @PathVariable Long orderId, @PathVariable Long productId) {
        OrderDTO order = this.removeProductFromOrderUseCase.execute(orderId, productId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/submit")
    @Operation(
            summary = "Submit an order for preparation",
            description = "Submits an order for preparation and return the order's data")
    public ResponseEntity<OrderDTO> submitOrder(@PathVariable Long orderId) {
        OrderDTO order = this.submitOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/prepare")
    @Operation(
            summary = "Start preparing an order",
            description = "Start preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> startPreparingOrder(@PathVariable Long orderId) {
        OrderDTO order = this.startPreparingOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/ready")
    @Operation(
            summary = "Finish preparing an order",
            description = "Finish preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> finishPreparingOrder(@PathVariable Long orderId) {
        OrderDTO order = this.finishPreparingOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/deliver")
    @Operation(
            summary = "Deliver an order",
            description = "Deliver an order and return the order's data")
    public ResponseEntity<OrderDTO> deliverOrder(@PathVariable Long orderId) {
        OrderDTO order = this.deliverOrderUseCase.execute(orderId);
        return ResponseEntity.ok().body(order);
    }
}
