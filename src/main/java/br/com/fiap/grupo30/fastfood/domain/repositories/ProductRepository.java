package br.com.fiap.grupo30.fastfood.domain.repositories;

import br.com.fiap.grupo30.fastfood.presentation.presenters.dto.ProductDTO;
import java.util.List;

public interface ProductRepository {

    List<ProductDTO> findProductsByCategoryId(Long categoryId);

    ProductDTO findById(Long id);

    ProductDTO insert(ProductDTO dto);

    ProductDTO update(Long id, ProductDTO dto);

    void delete(Long id);
}
