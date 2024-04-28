package br.com.fiap.grupo30.fastfood.resources;

import br.com.fiap.grupo30.fastfood.dto.CategoryDTO;
import br.com.fiap.grupo30.fastfood.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
@Tag(name = "Categories Resource", description = "RESTful API for managing categories.")
public class CategoryResource {

    @Autowired
    private CategoryService service;

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve a list of all registered categories")
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
}