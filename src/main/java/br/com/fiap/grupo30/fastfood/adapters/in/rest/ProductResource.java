package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.useCases.ProductUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
@Tag(name = "Products Resource", description = "RESTful API for managing products.")
public class ProductResource {

    private static final String PATH_VARIABLE_ID = "/{id}";

    private final ProductUseCase productUseCase;

    @Autowired
    public ProductResource(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description =
                    "Retrieve a list of all registered products or by categoryId "
                            + "via RequestParam. i.e., ?categoryId=1")
    public ResponseEntity<List<ProductDTO>> findProductsByCategoryId(
            @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
        List<ProductDTO> list = productUseCase.findProductsByCategoryId(categoryId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Get a product by ID",
            description = "Retrieve a specific product based on its ID")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        ProductDTO dto = productUseCase.findProductById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Create a new product and return the created product's data")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO dto) {
        ProductDTO dtoCreated = productUseCase.createProduct(dto);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(PATH_VARIABLE_ID)
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(dtoCreated);
    }

    @PutMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Update a product",
            description = "Update the data of an existing product based on its ID")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id, @RequestBody @Valid ProductDTO dto) {
        ProductDTO dtoUpdated = productUseCase.updateProduct(id, dto);
        return ResponseEntity.ok().body(dtoUpdated);
    }

    @DeleteMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Delete a product",
            description = "Delete an existing product based on its ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
