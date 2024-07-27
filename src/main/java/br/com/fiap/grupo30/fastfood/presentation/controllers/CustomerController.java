package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.customer.FindCustomerByCpfUseCase;
import br.com.fiap.grupo30.fastfood.domain.usecases.customer.RegisterNewCustomerUseCase;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CustomerDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/customers")
@Tag(name = "Customers Controller", description = "RESTful API for managing customers.")
public class CustomerController {

    private final FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    private final RegisterNewCustomerUseCase registerNewCustomerUseCase;

    @Autowired
    public CustomerController(
            FindCustomerByCpfUseCase findCustomerByCpfUseCase,
            RegisterNewCustomerUseCase registerNewCustomerUseCase) {
        this.findCustomerByCpfUseCase = findCustomerByCpfUseCase;
        this.registerNewCustomerUseCase = registerNewCustomerUseCase;
    }

    @GetMapping
    @Operation(summary = "Get a customer", description = "Retrieve a registered customer by cpf")
    public ResponseEntity<CustomerDTO> findCustomerByCpf(
            @RequestParam(value = "cpf", defaultValue = "0") String cpf) {
        CustomerDTO customer = this.findCustomerByCpfUseCase.execute(cpf);
        return ResponseEntity.ok().body(customer);
    }

    @PostMapping
    @Operation(
            summary = "Create a new customer",
            description = "Create a new customer and return the created customer's data")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO dto) {
        CustomerDTO createdCustomer =
                this.registerNewCustomerUseCase.execute(
                        dto.getName(), dto.getCpf(), dto.getEmail());
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{cpf}")
                        .buildAndExpand(dto.getCpf())
                        .toUri();
        return ResponseEntity.created(uri).body(createdCustomer);
    }
}
