package com.eshop.cart.controllers;

import com.eshop.cart.dtos.CartRequestDto;
import com.eshop.cart.dtos.CartResponseDto;
import com.eshop.cart.exceptions.CartIsEmptyException;
import com.eshop.cart.exceptions.CartNotFoundException;
import com.eshop.cart.exceptions.ProductNotFoundException;
import com.eshop.cart.models.Cart;
import com.eshop.cart.services.CartService;
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

    @PostMapping("/add")
    public ResponseEntity<CartResponseDto> addToCart(@RequestBody Cart cart,
                                                     @RequestHeader(value="Authorization") String header)
            throws ProductNotFoundException {

        CartResponseDto cartResponseDto = cartService.addToCart(cart, header);

        return ResponseEntity.ok().body(cartResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponseDto>> getCartDetails(@PathVariable("userId") Long userId)
            throws CartIsEmptyException {
        return ResponseEntity.ok().body(cartService.getCartDetails(userId));
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
}
