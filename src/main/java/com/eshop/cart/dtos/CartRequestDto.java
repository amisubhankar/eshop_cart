package com.eshop.cart.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDto {
    private Long id;
    private int quantity;
}
