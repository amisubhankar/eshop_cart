package com.eshop.cart.services;

import com.eshop.cart.dtos.CartResponseDto;
import com.eshop.cart.dtos.ProductsDto;
import com.eshop.cart.dtos.UserResponseDto;
import com.eshop.cart.exceptions.ProductNotFoundException;
import com.eshop.cart.exceptions.UserNotFoundException;
import com.eshop.cart.models.Cart;
import com.eshop.cart.repositories.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {

    @Autowired
    ICartRepository cartRepository;
    @Autowired
    RestTemplate restTemplate;

    public CartResponseDto addToCart(Cart cart, String header) throws ProductNotFoundException {

        //validate product
        ProductsDto product;
        try {
            product = restTemplate.getForObject("http://products/products/" + cart.getProduct_id(), ProductsDto.class);
        }catch (Exception e){
            throw new ProductNotFoundException();
        }

        //save to cart
        cart.setAmount(cart.getQuantity() * product.getPrice());
        cartRepository.save(cart);
        //make response object
        String message = product.getName() + " added successfully to your cart !!";
        CartResponseDto cartResponseDto = new CartResponseDto(product.getName(), cart.getQuantity(), cart.getAmount(), message);
        return cartResponseDto;
/*
//To call api gateway localhost, we have to pass Authorization token in the call
//getForObject will not support this, so we have to use exchange().

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", header);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<ProductsDto> response = restTemplate.exchange(
                "http://localhost:9090/products/"+cart.getProduct_id(),
                HttpMethod.GET, requestEntity, ProductsDto.class);
 */
    }
}
