package com.eshop.cart.repositories;

import com.eshop.cart.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
}
