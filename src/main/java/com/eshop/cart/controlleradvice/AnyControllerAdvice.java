package com.eshop.cart.controlleradvice;

import com.eshop.cart.exceptions.CartIsEmptyException;
import com.eshop.cart.exceptions.CartNotFoundException;
import com.eshop.cart.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AnyControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found !!");
    }

    @ExceptionHandler(CartIsEmptyException.class)
    public ResponseEntity<String> handleCartEmpty(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Your cart is empty !!");
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleCartNotFound(){
        return ResponseEntity.badRequest().body("Cart not found !!");
    }
}
