package com.ecommerce.application.cart.controller;


import com.ecommerce.application.cart.dto.CartDtoRequest;
import com.ecommerce.application.cart.dto.CartDtoResponse;

import com.ecommerce.application.cart.dto.CartUpdateDto;
import com.ecommerce.application.cart.service.CartService;
import com.ecommerce.application.exception.BusinessLogicException;
import com.ecommerce.application.exception.ProductDoesNotExistException;
import com.ecommerce.application.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartDtoRequest cartDtoRequest){
        try{
            CartDtoResponse myCart = cartService.addItemToCart(cartDtoRequest);
            return ResponseEntity.ok().body(myCart);
        } catch (BusinessLogicException | UserNotFoundException | ProductDoesNotExistException | IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updateCart(@RequestBody CartUpdateDto cartUpdateDto){
        try{
            CartDtoResponse myCart = cartService.updateCart(cartUpdateDto);
            return ResponseEntity.status(HttpStatus.OK).body(myCart);
        } catch (BusinessLogicException | UserNotFoundException | IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
