package br.com.fiap.grupo30.fastfood.adapters.in.rest;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.application.useCases.CategoryUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Categories Resource", description = "RESTful API for managing categories.")
public class CategoryResource {

    private final CategoryUseCase categoryUseCase;

    @Autowired
    public CategoryResource(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all registered categories")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = categoryUseCase.findProducts();
        return ResponseEntity.ok().body(list);
    }
}
