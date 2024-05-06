package br.com.fiap.grupo30.fastfood.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Category category;
}
