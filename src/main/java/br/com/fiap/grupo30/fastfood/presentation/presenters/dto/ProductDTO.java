package br.com.fiap.grupo30.fastfood.presentation.presenters.dto;

import br.com.fiap.grupo30.fastfood.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;

    @NotBlank(message = "Campo requirido")
    private String name;

    @NotBlank(message = "Campo requirido")
    private String description;

    @Positive(message = "Preço deve ser um valor positivo") private Double price;

    @NotBlank(message = "Campo requirido")
    private String imgUrl;

    private CategoryDTO category;

    public ProductDTO(Product entity) {
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        category = new CategoryDTO(entity.getCategory());
    }
}
