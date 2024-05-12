package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.CustomerDTO;
import br.com.fiap.grupo30.fastfood.application.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customers")
@Tag(name = "Customer Resource", description = "RESTful API for managing customers.")
public class CustomerResource {

    private static final String PATH_VARIABLE_ID = "/{id}";

    private final CustomerService customerService;

    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @Operation(summary = "Get a customer", description = "Retrieve a registered customer by cpf")
    public ResponseEntity<CustomerDTO> findCustomerByCpf(
            @RequestParam(value = "cpf", defaultValue = "0") String cpf) {
        CustomerDTO dto = customerService.findCustomerByCpf(cpf);
        return ResponseEntity.ok().body(dto);
    }
}
