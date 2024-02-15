package com.ecommerce.application.product.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private String name;
    private String description;
    private int quantity;
    private double price;
    private MultipartFile image;

}
