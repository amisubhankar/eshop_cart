package com.eshop.cart.dtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@JsonSerialize
public class CartResponseDto implements Serializable {
    private String productName;
    private int quantity;
    private float amount;
    private String message;
}
