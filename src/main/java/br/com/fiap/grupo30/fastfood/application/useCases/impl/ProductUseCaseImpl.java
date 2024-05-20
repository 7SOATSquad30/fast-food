package br.com.fiap.grupo30.fastfood.application.useCases.impl;

import br.com.fiap.grupo30.fastfood.application.dto.ProductDTO;
import br.com.fiap.grupo30.fastfood.application.services.ProductService;
import br.com.fiap.grupo30.fastfood.application.useCases.ProductUseCase;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductService productService;

    public ProductUseCaseImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<ProductDTO> findProductsByCategoryId(Long categoryId) {
        return productService.findProductsByCategoryId(categoryId);
    }

    @Override
    public ProductDTO findProductById(Long id) {
        return productService.findById(id);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        return productService.insert(productDTO);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        return productService.update(id, dto);
    }

    @Override
    public void deleteProduct(Long id) {
        productService.delete(id);
    }
}
