package com.ecommerce.application.cart.dto;

import com.ecommerce.application.item.Item;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class  CartDtoResponse {
    private List<Item> cartItem;
    private double totalPrice;
}
