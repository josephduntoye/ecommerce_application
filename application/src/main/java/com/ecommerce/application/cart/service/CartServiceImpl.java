package com.ecommerce.application.cart.service;

import com.ecommerce.application.cart.Cart;
import com.ecommerce.application.cart.dto.CartDtoRequest;
import com.ecommerce.application.cart.dto.CartDtoResponse;
import com.ecommerce.application.cart.dto.CartUpdateDto;
import com.ecommerce.application.cart.enumPackage.QunatityOperation;
import com.ecommerce.application.cart.repository.CartRepository;
import com.ecommerce.application.exception.BusinessLogicException;
import com.ecommerce.application.exception.ProductDoesNotExistException;
import com.ecommerce.application.exception.UserNotFoundException;
import com.ecommerce.application.item.Item;
import com.ecommerce.application.product.Product;
import com.ecommerce.application.product.repository.ProductRepository;
import com.ecommerce.application.user.model.AppUser;
import com.ecommerce.application.user.repository.AppUserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartRepository cartRepository;


    @Override
    public CartDtoResponse addItemToCart(CartDtoRequest cartDtoRequest) throws UserNotFoundException, ProductDoesNotExistException, BusinessLogicException {

        //check if user exist
        Optional<AppUser> query = appUserRepository.findById(cartDtoRequest.getUserId());
        // Throw an exception if user is null
        if (query.isEmpty()) {
            throw new UserNotFoundException("User with Id " + cartDtoRequest.getUserId() + " not found.");
        }
        AppUser existingUser = query.get();

        // get user cart
        Cart mycart = existingUser.getMyCart();
        //check if product exist
        Optional<Product> querryProduct = productRepository.findById(cartDtoRequest.getProductId());
        if (querryProduct.isEmpty()) {
            throw new ProductDoesNotExistException("Product with the Id " + cartDtoRequest.getProductId() + " not found.");
        }
        Product product = querryProduct.get();
        if (!quantityIsValid(product, cartDtoRequest.getQuantity())) {
            throw new BusinessLogicException("Quantity too large.");
        }

        Item cartItem = new Item(product, cartDtoRequest.getQuantity());
        mycart.addItem(cartItem);
        mycart.setTotalPrice(mycart.getTotalPrice() + calculateItemPrice(cartItem));
        cartRepository.save(mycart);
        return buildCartResponse(mycart);
    }

    private boolean quantityIsValid(Product product, int quantity) {
        return quantity <= product.getQuantity();

    }


    private CartDtoResponse buildCartResponse(Cart cart) {
        return CartDtoResponse.builder()
                .cartItem(cart.getItemList())
                .totalPrice(cart.getTotalPrice())
                .build();
    }

    private Double calculateItemPrice(Item item) {

        return item.getProduct().getPrice() * item.getQuantityAdded();
    }


    @Override
    public CartDtoResponse updateCart(CartUpdateDto cartUpdateDto) throws UserNotFoundException, BusinessLogicException {

        //get a cart by userId
        AppUser appUser = appUserRepository.findById(cartUpdateDto.getUserId()).orElse(null);
        if(appUser == null){
            throw new UserNotFoundException("User with ID " + cartUpdateDto.getUserId() + " not found");
        }

        //get user cart
        Cart mycart = appUser.getMyCart();
        //find item in cart
        Item item = findItem(cartUpdateDto.getItemId(), mycart).orElse(null);
        if(item == null){
            throw new BusinessLogicException("Item not in cart");
        }
        if(cartUpdateDto.getQuantity() == QunatityOperation.INCREASE){
            item.setQuantityAddedToCart(item.getQuantityAdded()+1);
            mycart.setTotalPrice(mycart.getTotalPrice() - item.getProduct().getPrice());
        }
        else if(cartUpdateDto.getQuantity() == QunatityOperation.DECREASE){
            item.setQuantityAddedToCart(item.getQuantityAdded()+1);
            mycart.setTotalPrice(mycart.getTotalPrice() - item.getProduct().getPrice());
        }

        cartRepository.save(mycart);
        return buildCartResponse(mycart);

        // find the item within the cart
    }

    private Optional<Item> findItem(Long itemId, Cart cart){
        Predicate<Item> itemPredicate = i -> i.getId().equals(itemId);
        return cart.getItemList().stream().filter(itemPredicate).findFirst();
    }

}
