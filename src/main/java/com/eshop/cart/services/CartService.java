package com.eshop.cart.services;

import com.eshop.cart.dtos.CartRequestDto;
import com.eshop.cart.dtos.CartResponseDto;
import com.eshop.cart.dtos.ProductsDto;
import com.eshop.cart.exceptions.CartIsEmptyException;
import com.eshop.cart.exceptions.CartNotFoundException;
import com.eshop.cart.exceptions.ProductNotFoundException;
import com.eshop.cart.models.Cart;
import com.eshop.cart.repositories.ICartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    ICartRepository cartRepository;
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    public CartResponseDto addToCart(Cart cart, String header) throws ProductNotFoundException {

        //validate product
        ProductsDto product;
        try {
            product = restTemplate.getForObject("http://products/products/" + cart.getProductId(), ProductsDto.class);
        }catch (Exception e){
            throw new ProductNotFoundException();
        }

        //save to cart
        cart.setAmount(cart.getQuantity() * product.getPrice());
        cartRepository.save(cart);
        //make response object
        String message = product.getName() + " added successfully to your cart !!";
        CartResponseDto cartResponseDto = new CartResponseDto(product.getName() ,cart.getQuantity(), cart.getAmount(), message);
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

    public List<CartResponseDto> getCartDetailsbyUser(Long userId) throws CartIsEmptyException {
        if(redisTemplate.opsForHash().hasKey("carts-"+userId, "something") == false) {

            List<Cart> cartList = cartRepository.findByUserId(userId);

            if (cartList.isEmpty()) {
                throw new CartIsEmptyException();
            }

            List<CartResponseDto> cartResponseDtoList = new ArrayList<>();
            for (Cart cart : cartList) {
                ProductsDto product = restTemplate.getForObject("http://products/products/" + cart.getProductId(),
                        ProductsDto.class);
                cartResponseDtoList.add(new CartResponseDto(product.getName(), cart.getQuantity(), cart.getAmount(), ""));
            }

            redisTemplate.opsForHash().put("carts-"+userId, "something", cartResponseDtoList);
        }

        return (List<CartResponseDto>) redisTemplate.opsForHash().get("carts-"+userId, "something");
    }

    public void updateCart(CartRequestDto cartRequestDto) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(cartRequestDto.getId());

        if(optionalCart.isEmpty()){
            throw new CartNotFoundException();
        }

        Cart cart = optionalCart.get();
        float itemPrice = cart.getAmount() / cart.getQuantity() ;

        cart.setQuantity(cartRequestDto.getQuantity());
        cart.setAmount(cartRequestDto.getQuantity() * itemPrice);
        cartRepository.save(cart);
    }

    public void deleteCart(Long id) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(id);

        if(optionalCart.isEmpty()){
            throw new CartNotFoundException();
        }

        cartRepository.deleteById(id);
    }

    public Cart getCartDetails(Long cartId) {
        //skipping cartId validation
        return cartRepository.findById(cartId).get();
    }
}
