package br.com.fiap.grupo30.fastfood.presentation.controllers;

import br.com.fiap.grupo30.fastfood.domain.usecases.category.ListAllCategoriesInMenuUseCase;
import br.com.fiap.grupo30.fastfood.infrastructure.gateways.CategoryGateway;
import br.com.fiap.grupo30.fastfood.infrastructure.persistence.repositories.JpaCategoryRepository;
import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.CategoryDTO;
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
    public final JpaCategoryRepository jpaCategoryRepository;

    @Autowired
    public CategoryController(
            ListAllCategoriesInMenuUseCase listAllCategoriesInMenuUseCase,
            JpaCategoryRepository jpaCategoryRepository) {
        this.listAllCategoriesInMenuUseCase = listAllCategoriesInMenuUseCase;
        this.jpaCategoryRepository = jpaCategoryRepository;
    }

    @GetMapping
    @Operation(
            summary = "Get all categories",
            description = "Retrieve a list of all registered categories")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        CategoryGateway categoryGateway = new CategoryGateway(jpaCategoryRepository);
        List<CategoryDTO> categories = this.listAllCategoriesInMenuUseCase.execute(categoryGateway);
        return ResponseEntity.ok().body(categories);
    }
}
