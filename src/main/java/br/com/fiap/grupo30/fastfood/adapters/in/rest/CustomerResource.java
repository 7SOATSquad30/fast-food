package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.useCases.CustomerUseCase;
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
@Tag(name = "Customers Resource", description = "RESTful API for managing customers.")
public class CustomerResource {

    private static final String PATH_VARIABLE_ID = "/{id}";

    private final CustomerUseCase customerUseCase;

    @Autowired
    public CustomerResource(CustomerUseCase customerUseCase) {
        this.customerUseCase = customerUseCase;
    }

    @GetMapping
    @Operation(summary = "Get a customer", description = "Retrieve a registered customer by cpf")
    public ResponseEntity<CustomerDTO> findCustomerByCpf(
            @RequestParam(value = "cpf", defaultValue = "0") String cpf) {
        CustomerDTO dto = customerUseCase.findCustomerByCpf(cpf);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(
            summary = "Create a new customer",
            description = "Create a new customer and return the created customer's data")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody @Valid CustomerDTO dto) {
        CustomerDTO dtoCreated = customerUseCase.createCustomer(dto);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(PATH_VARIABLE_ID)
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(dtoCreated);
    }
}
