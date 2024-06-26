package br.com.fiap.grupo30.fastfood.domain.services;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    List<ProductDTO> findProductsByCategoryId(Long categoryId);

    ProductDTO findById(Long id);

    ProductDTO insert(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    void delete(Long id);
}
