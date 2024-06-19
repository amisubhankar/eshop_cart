package com.eshop.cart.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartResponseDto {
    private String productName;
    private int quantity;
    private float amount;
    private String message;
}
