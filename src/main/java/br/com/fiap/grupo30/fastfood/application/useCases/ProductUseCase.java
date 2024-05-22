package br.com.fiap.grupo30.fastfood.application.useCases;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import java.util.List;

public interface ProductUseCase {

    List<ProductDTO> findProductsByCategoryId(Long categoryId);

    ProductDTO findProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO dto);

    void deleteProduct(Long id);
}
