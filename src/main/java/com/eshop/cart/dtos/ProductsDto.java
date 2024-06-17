package com.eshop.cart.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDto {
    private Long id;
    private String name;
    private String description;
    private String image;
    private float price;
    private int availableQuantity;

}
