package com.ecommerce.application.product.dto;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private MultipartFile image;

}
