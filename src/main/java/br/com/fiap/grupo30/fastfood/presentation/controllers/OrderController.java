package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.entities.Customer;
import br.com.fiap.grupo30.fastfood.domain.usecases.customer.FindCustomerByCpfUseCase;
import br.com.fiap.grupo30.fastfood.domain.usecases.order.*;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.AddCustomerCpfRequest;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.AddOrderProductRequest;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.OrderDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.OrderDTOMapper;
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
    private final ListOrdersUseCase listAllOrdersUseCase;
    private final StartPreparingOrderUseCase startPreparingOrderUseCase;
    private final FinishPreparingOrderUseCase finishPreparingOrderUseCase;
    private final DeliverOrderUseCase deliverOrderUseCase;
    private final FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    private final OrderDTOMapper orderDTOMapper;

    @Autowired
    public OrderController(
            StartNewOrderUseCase startNewOrderUseCase,
            AddProductToOrderUseCase addProductToOrderUseCase,
            RemoveProductFromOrderUseCase removeProductFromOrderUseCase,
            GetOrderUseCase getOrderUseCase,
            SubmitOrderUseCase submitOrderUseCase,
            ListOrdersUseCase listAllOrdersUseCase,
            StartPreparingOrderUseCase startPreparingOrderUseCase,
            FinishPreparingOrderUseCase finishPreparingOrderUseCase,
            DeliverOrderUseCase deliverOrderUseCase,
            FindCustomerByCpfUseCase findCustomerByCpfUseCase,
            OrderDTOMapper orderDTOMapper) {
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
        this.orderDTOMapper = orderDTOMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get all orders",
            description =
                    "Get a list of all registered orders sorted by date and status or by status"
                            + " sorted by date via RequestParam. i.e., ?status=")
    public ResponseEntity<List<OrderDTO>> findOrdersByStatus(
            @RequestParam(value = "status", required = false) String status) {
        List<OrderDTO> list =
                listAllOrdersUseCase.execute(status).stream().map(orderDTOMapper::mapTo).toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{orderId}")
    @Operation(
            summary = "Get an order by ID",
            description = "Retrieve a specific order based on its ID")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long orderId) {
        OrderDTO order = orderDTOMapper.mapTo(getOrderUseCase.execute(orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    @Operation(
            summary = "Create a new order",
            description = "Create a new order and return the new order's data")
    public ResponseEntity<OrderDTO> startNewOrder(
            @RequestBody(required = false) AddCustomerCpfRequest request) {
        Customer customer = null;
        if (request != null && request.getCpf() != null && !request.getCpf().isEmpty()) {
            customer = findCustomerByCpfUseCase.execute(request.getCpf());
        }
        OrderDTO order = orderDTOMapper.mapTo(startNewOrderUseCase.execute(customer));
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
                orderDTOMapper.mapTo(
                        addProductToOrderUseCase.execute(
                                orderId, request.getProductId(), request.getQuantity()));
        return ResponseEntity.ok().body(order);
    }

    @DeleteMapping(value = "/{orderId}/products/{productId}")
    @Operation(
            summary = "Remove a product from an order",
            description = "Removes a product from an order")
    public ResponseEntity<OrderDTO> removeProduct(
            @PathVariable Long orderId, @PathVariable Long productId) {
        OrderDTO order =
                orderDTOMapper.mapTo(removeProductFromOrderUseCase.execute(orderId, productId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/submit")
    @Operation(
            summary = "Submit an order for preparation",
            description = "Submits an order for preparation and return the order's data")
    public ResponseEntity<OrderDTO> submitOrder(@PathVariable Long orderId) {
        OrderDTO order = orderDTOMapper.mapTo(submitOrderUseCase.execute(orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/prepare")
    @Operation(
            summary = "Start preparing an order",
            description = "Start preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> startPreparingOrder(@PathVariable Long orderId) {
        OrderDTO order = orderDTOMapper.mapTo(startPreparingOrderUseCase.execute(orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/ready")
    @Operation(
            summary = "Finish preparing an order",
            description = "Finish preparing an order and return the order's data")
    public ResponseEntity<OrderDTO> finishPreparingOrder(@PathVariable Long orderId) {
        OrderDTO order = orderDTOMapper.mapTo(finishPreparingOrderUseCase.execute(orderId));
        return ResponseEntity.ok().body(order);
    }

    @PostMapping(value = "/{orderId}/deliver")
    @Operation(
            summary = "Deliver an order",
            description = "Deliver an order and return the order's data")
    public ResponseEntity<OrderDTO> deliverOrder(@PathVariable Long orderId) {
        OrderDTO order = orderDTOMapper.mapTo(deliverOrderUseCase.execute(orderId));
        return ResponseEntity.ok().body(order);
    }
}
