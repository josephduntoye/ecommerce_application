package com.ecommerce.application.cart.service;

import com.ecommerce.application.cart.dto.CartDtoRequest;
import com.ecommerce.application.cart.dto.CartDtoResponse;
import com.ecommerce.application.cart.dto.CartUpdateDto;
import com.ecommerce.application.exception.BusinessLogicException;
import com.ecommerce.application.exception.ProductDoesNotExistException;
import com.ecommerce.application.exception.UserNotFoundException;

public interface CartService {
    CartDtoResponse addItemToCart(CartDtoRequest cartDtoRequest) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException;
    CartDtoResponse updateCart(CartUpdateDto cartUpdateDto) throws UserNotFoundException, BusinessLogicException;
    }
