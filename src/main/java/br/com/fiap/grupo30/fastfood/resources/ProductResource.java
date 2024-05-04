package br.com.fiap.grupo30.fastfood.resources;

import br.com.fiap.grupo30.fastfood.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.services.ProductService;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    private static final String PATH_VARIABLE_ID = "/{id}";

    @Autowired private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll(
            @RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
        List<ProductDTO> list = service.findAll(categoryId);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = PATH_VARIABLE_ID)
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {
        ProductDTO dtoCreated = service.insert(dto);
        URI uri =
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path(PATH_VARIABLE_ID)
                        .buildAndExpand(dto.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(dtoCreated);
    }

    @PutMapping(value = PATH_VARIABLE_ID)
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        ProductDTO dtoUpdated = service.update(id, dto);
        return ResponseEntity.ok().body(dtoUpdated);
    }

    @DeleteMapping(value = PATH_VARIABLE_ID)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
