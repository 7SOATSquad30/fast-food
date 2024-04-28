package br.com.fiap.grupo30.fastfood.resources;

import br.com.fiap.grupo30.fastfood.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Products Resource", description = "RESTful API for managing products.")
public class ProductResource {

    @Autowired
    private ProductService service;

    @GetMapping
    @Operation(summary = "Get all products",
            description = "Retrieve a list of all registered products or by categoryId "
                    + "via RequestParam. i.e., ?categoryId=1")
    public ResponseEntity<List<ProductDTO>> findAll(
            @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId
    ) {
        List<ProductDTO> list = service.findAll(categoryId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a product by ID", description = "Retrieve a specific product based on its ID")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Create a new product and return the created product's data")
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update a product", description = "Update the data of an existing product based on its ID")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a product", description = "Delete an existing product based on its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}