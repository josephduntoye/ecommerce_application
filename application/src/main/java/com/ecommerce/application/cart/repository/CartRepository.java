package com.ecommerce.application.cart.repository;

import com.ecommerce.application.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
