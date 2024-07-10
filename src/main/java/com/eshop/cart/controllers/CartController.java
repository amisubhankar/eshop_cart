package com.eshop.cart.controllers;

import com.eshop.cart.dtos.CartDeleteRequestDto;
import com.eshop.cart.dtos.CartRequestDto;
import com.eshop.cart.dtos.CartResponseDto;
import com.eshop.cart.exceptions.CartIsEmptyException;
import com.eshop.cart.exceptions.CartNotFoundException;
import com.eshop.cart.exceptions.ProductNotFoundException;
import com.eshop.cart.models.Cart;
import com.eshop.cart.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody Cart cart,
                                                     @RequestHeader(value="Authorization") String header)
            throws ProductNotFoundException {

        CartResponseDto cartResponseDto = cartService.addToCart(cart, header);

        return ResponseEntity.ok().body(cartResponseDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartResponseDto>> getCartDetailsbyUser(@PathVariable("userId") Long userId)
            throws CartIsEmptyException {
        List<CartResponseDto> list = cartService.getCartDetailsbyUser(userId);
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCart(@RequestBody CartRequestDto cartRequestDto) throws CartNotFoundException {
        cartService.updateCart(cartRequestDto);

        return ResponseEntity.ok().body("Cart updated successfully !!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") Long id) throws CartNotFoundException {
        cartService.deleteCart(id);

        return ResponseEntity.ok().body("Cart deleted successfully !!");
    }

    @DeleteMapping("/delete/afterorder")
    public ResponseEntity<String> deleteCartAfterOrderIsPlaced(@RequestParam(name = "cartIds") String cartIds){
        CartDeleteRequestDto cartDeleteRequestDto = null;
        try {
            cartDeleteRequestDto = objectMapper.readValue(cartIds, CartDeleteRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        cartService.deleteCartAfterOrderIsPlaced(cartDeleteRequestDto);
        System.out.println("hello");
        return ResponseEntity.ok().body("Given cart ids are deleted successfully !!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cart> getCartDetails(@PathVariable("id") Long cartId){
        return ResponseEntity.ok().body(cartService.getCartDetails(cartId));
    }
}
