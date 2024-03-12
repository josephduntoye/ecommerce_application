package com.ecommerce.application.cart.dto;

import com.ecommerce.application.cart.enumPackage.QunatityOperation;
import lombok.Data;

@Data
public class CartUpdateDto {
    private Long userId;
    private Long itemId;
    private QunatityOperation quantity;
}
