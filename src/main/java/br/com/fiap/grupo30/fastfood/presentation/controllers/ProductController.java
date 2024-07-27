package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.entities.Product;
import br.com.fiap.grupo30.fastfood.domain.usecases.product.*;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.presentation.presenters.mapper.impl.ProductDTOMapper;
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

    private final ProductDTOMapper productDTOMapper;

    @Autowired
    public ProductController(
            ListProductsByCategoryUseCase listProductsByCategoryUseCase,
            GetProductUseCase getProductUseCase,
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            DeleteProductUseCase deleteProductUseCase,
            ProductDTOMapper productDTOMapper) {

        this.listProductsByCategoryUseCase = listProductsByCategoryUseCase;
        this.getProductUseCase = getProductUseCase;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
        this.productDTOMapper = productDTOMapper;
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description =
                    "Retrieve a list of all registered products or by categoryId "
                            + "via RequestParam. i.e., ?categoryId=1")
    public ResponseEntity<List<ProductDTO>> findProductsByCategoryId(
            @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
        List<ProductDTO> list =
                this.listProductsByCategoryUseCase.execute(categoryId).stream()
                        .map(this.productDTOMapper::mapTo)
                        .toList();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Get a product by ID",
            description = "Retrieve a specific product based on its ID")
    public ResponseEntity<ProductDTO> findProductById(@PathVariable Long id) {
        ProductDTO dto = this.productDTOMapper.mapTo(this.getProductUseCase.execute(id));
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @Operation(
            summary = "Create a new product",
            description = "Create a new product and return the created product's data")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO dto) {
        Product domainObj = this.productDTOMapper.mapFrom(dto);
        ProductDTO dtoCreated =
                this.productDTOMapper.mapTo(this.createProductUseCase.execute(domainObj));
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
        Product domainObj = this.productDTOMapper.mapFrom(dto);
        ProductDTO dtoUpdated =
                this.productDTOMapper.mapTo(this.updateProductUseCase.execute(id, domainObj));
        return ResponseEntity.ok().body(dtoUpdated);
    }

    @DeleteMapping(value = PATH_VARIABLE_ID)
    @Operation(
            summary = "Delete a product",
            description = "Delete an existing product based on its ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        this.deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
