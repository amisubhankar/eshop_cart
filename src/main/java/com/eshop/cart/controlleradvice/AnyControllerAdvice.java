package com.eshop.cart.controlleradvice;

import com.eshop.cart.exceptions.ProductNotFoundException;
import com.eshop.cart.exceptions.UserNotFoundException;
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

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found !!");
    }
}
