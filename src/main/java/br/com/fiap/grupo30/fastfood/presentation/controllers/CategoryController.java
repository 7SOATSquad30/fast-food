package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.application.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.domain.usecases.product.ListAllCategoriesInMenuUseCase;
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
@Tag(name = "Categories Controller", description = "RESTful API for managing categories.")
public class CategoryController {

    private final ListAllCategoriesInMenuUseCase listAllCategoriesInMenuUseCase;

    @Autowired
    public CategoryController(ListAllCategoriesInMenuUseCase listAllCategoriesInMenuUseCase) {
        this.listAllCategoriesInMenuUseCase = listAllCategoriesInMenuUseCase;
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all registered categories")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = this.listAllCategoriesInMenuUseCase.execute();
        return ResponseEntity.ok().body(list);
    }
}
