package com.eshop.cart.controllers;

import com.eshop.cart.dtos.CartResponseDto;
import com.eshop.cart.exceptions.ProductNotFoundException;
import com.eshop.cart.exceptions.UserNotFoundException;
import com.eshop.cart.models.Cart;
import com.eshop.cart.services.CartService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody Cart cart,
                                                     @RequestHeader(value="Authorization") String header)
            throws ProductNotFoundException {

        CartResponseDto cartResponseDto = cartService.addToCart(cart, header);

        return ResponseEntity.ok().body(cartResponseDto);
    }

    @GetMapping("/")
    public String test(){
        return "Working!!";
    }
}
