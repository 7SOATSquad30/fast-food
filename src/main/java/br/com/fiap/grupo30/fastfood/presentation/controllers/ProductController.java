package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CategoryGateway;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
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
@Tag(name = "Products Controller", description = "RESTful API for managing products.")
public class ProductController {

    private static final String PATH_VARIABLE_ID = "/{id}";

    private final ListProductsByCategoryUseCase listProductsByCategoryUseCase;
    private final GetProductUseCase getProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @Autowired
    public ProductController(
            ListProductsByCategoryUseCase listProductsByCategoryUseCase,
            GetProductUseCase getProductUseCase,
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase) {

        this.listProductsByCategoryUseCase = listProductsByCategoryUseCase;
        this.getProductUseCase = getProductUseCase;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description =
                    "Retrieve a list of all registered products or by categoryId "
                            + "via RequestParam. i.e., ?categoryId=1")
    public ResponseEntity<List<ProductDTO>> findProductsByCategoryId(
            @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
        ProductGateway productGateway = new ProductGateway();
        List<ProductDTO> products = this.listProductsByCategoryUseCase.execute(productGateway, categoryId);
        return ResponseEntity.ok().body(products);
    }

    @GetMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Get a product by ID",
            description = "Retrieve a specific product based on its ID")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        ProductGateway productGateway = new ProductGateway();
        ProductDTO dto = this.getProductUseCase.execute(productGateway, id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Create a new product and return the created product's data")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO dto) {
        ProductGateway productGateway = new ProductGateway();
        CategoryGateway categoryGateway= new CategoryGateway();

        ProductDTO dtoCreated =
                this.createProductUseCase.execute(
                        productGateway, 
                        categoryGateway,
                        dto.getName(),
                        dto.getDescription(),
                        dto.getPrice(),
                        dto.getImgUrl(),
                        dto.getCategory());
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(PATH_VARIABLE_ID)
                        .buildAndExpand(dto.getProductId())
                        .toUri();
        return ResponseEntity.created(uri).body(dtoCreated);
    }

    @PutMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Update a product",
            description = "Update the data of an existing product based on its ID")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id, @RequestBody @Valid ProductDTO dto) {
        ProductGateway productGateway = new ProductGateway();
        CategoryGateway categoryGateway= new CategoryGateway();
        ProductDTO dtoUpdated =
                this.updateProductUseCase.execute(
                        productGateway,
                        categoryGateway,
                        id,
                        dto.getName(),
                        dto.getDescription(),
                        dto.getPrice(),
                        dto.getImgUrl(),
                        dto.getCategory());
        return ResponseEntity.ok().body(dtoUpdated);
    }

    @DeleteMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Delete a product",
            description = "Delete an existing product based on its ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        ProductGateway productGateway = new ProductGateway();
        this.deleteProductUseCase.execute(productGateway, id);
        return ResponseEntity.noContent().build();
    }
}
